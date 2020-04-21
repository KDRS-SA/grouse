package no.kdrs.grouse.service;

import no.kdrs.grouse.utils.PatchObject;
import no.kdrs.grouse.utils.PatchObjects;
import no.kdrs.grouse.utils.RoleValidator;
import no.kdrs.grouse.utils.exception.BadRequestException;
import no.kdrs.grouse.utils.exception.ConcurrencyException;
import no.kdrs.grouse.utils.exception.InternalException;
import no.kdrs.grouse.utils.exception.PatchMisconfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpHeaders.ETAG;

@Service
public class GrouseService {

    @Autowired
    ACLService aclService;

    @Autowired
    RoleValidator roleValidator;

    @Autowired
    private PasswordEncoder encoder;


    private static final Logger logger =
            LoggerFactory.getLogger(GrouseService.class);

    public String getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            return context.getAuthentication().getName();
        }
        throw new InternalException("Unable to find security context in " +
                "getUser()");
    }

    /**
     * An implementation of part of the PATCH (RFC 6902) standard
     * <p>
     * [
     * { "op": "replace", "path": "/requirementText", "value": "hello"},
     * ]
     * <p>
     * In this application "replace" is the only operation we support. For
     * this application this is acceptable.
     *
     * @param object       The object to update
     * @param patchObjects Multiple pathObject commands in one object
     *                     (contains an array)
     * @return The object after it was persisted
     * @throws SecurityException if denied
     *                           NoSuchMethodException method does not exist
     *                           IllegalArgumentException problem with argument
     *                           IllegalAccessException problem with access
     *                           InvocationTargetException if it can't handle the syntax for some reason
     */
    protected Object handlePatch(Object object, PatchObjects patchObjects) {
        for (PatchObject patchObject : patchObjects.getPatchObjects()) {
            // Only support replace
            if ("replace".equalsIgnoreCase(patchObject.getOp())
                    // Make sure that path contains a column to change
                    && null != patchObject.getPath()
                    // and that there is at least one letter after the slash
                    && patchObject.getPath().length() > 1) {
                String path = patchObject.getPath();
                if ("/".equals(path.substring(0, 1))) {
                    path = path.substring(1);
                }
                if (!checkColumnUpdatable(path)) {
                    String errorMessage = object.getClass().getName() +
                            ": " + path + " is not allowed to be updated";
                    logger.error(errorMessage);
                    throw new PatchMisconfigurationException(errorMessage);
                }
                String baseMethodName = path.substring(0, 1)
                        .toUpperCase() + path.substring(1);
                String setMethodName = "set" + baseMethodName;
                String getMethodName = "get" + baseMethodName;
                try {
                    Method getMethod =
                            object.getClass().getMethod(getMethodName);
                    Method setMethod =
                            object.getClass().getMethod(setMethodName,
                                    getMethod.getReturnType());
                    Method versionMethod =
                            object.getClass().getMethod("setVersion",
                                    Long.class);
                    // If the variable (path) you are trying to update is a
                    // password then you have to encode the new password
                    if (PASSWORD.equalsIgnoreCase(path)) {
                        // Not possible to set password to null
                        if (null != patchObject.getValue()) {
                            setMethod.invoke(object,
                                    encoder.encode(patchObject.getValue()
                                            .toString()));
                        }
                    } else {
                        // null could likely be handled in a better way. This
                        // is left to later refactoring, if the patch is to be
                        // revisited
                        if (null != patchObject.getValue()) {
                            setMethod.invoke(object, patchObject.getValue());
                        } else {
                            setMethod.invoke(object, new Object[]{null});
                        }
                        versionMethod.invoke(object, getETag());
                    }
                } catch (SecurityException | NoSuchMethodException |
                        IllegalArgumentException | IllegalAccessException |
                        InvocationTargetException e) {
                    // Avoid concurrency exception from being swallowed as an
                    // InvocationTargetException
                    if (e.getCause() instanceof ConcurrencyException) {
                        throw (ConcurrencyException) e.getCause();
                    }
                    String error = "Cannot find internal method from Patch : " +
                            patchObject.toString() + " : " + e.getMessage();
                    logger.error(error);
                    throw new PatchMisconfigurationException(error);
                }
            } else {
                String error = "Cannot handle this PatchObject : " +
                        patchObject.toString();
                logger.error(error);
                throw new PatchMisconfigurationException(error);
            }
        }
        return object;
    }

    protected String getServletPath() {
        return ((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest().getServletPath();
    }

    protected Long getETag() {
        String etagValue = ((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest().getHeader(ETAG);
        if (etagValue == null) {
            throw new BadRequestException("ETAG missing in PATCH request");
        }
        return parseETAG(etagValue);
    }

    /**
     * Retrieve the etag value but without the quotes. An ETAG should be
     * quoted e.g. "0"
     *
     * @param quotedETAG the incoming etag from the header
     * @return the etag value without quotes
     */
    private Long parseETAG(String quotedETAG) {
        long etagVal;
        if (quotedETAG != null) {
            try {
                etagVal = Long.parseLong(
                        quotedETAG.replaceAll("\"", ""));
            } catch (NumberFormatException nfe) {
                throw new PatchMisconfigurationException(ETAG_NAN);
            }
        } else {
            throw new PatchMisconfigurationException(ETAG_NAN);
        }
        if (etagVal < 0) {
            throw new PatchMisconfigurationException(ETAG_VALUE_LESS_0);
        }
        return etagVal;
    }

    // This must be implemented in the child class
    protected boolean checkColumnUpdatable(String path) {
        String errorMessage = getServletPath() + " has no checkColumnUpdatable";
        logger.error(errorMessage);
        throw new BadRequestException(errorMessage);
    }

    public void checkOwner(String ownedBy, String objectType) {
        if (roleValidator.isAdmin()) {
            return;
        }
        if (!getUser().equals(ownedBy)) {
            String error = NO_ACCESS_OBJECT + objectType;
            logger.error(error);
            throw new AccessDeniedException(error);
        }
    }

    /**
     * A user with administrator role has access to any object
     *
     * @param objectId UUID of the object to check
     */
    public void checkAccess(UUID objectId) {
        if (roleValidator.isAdmin()) {
            return;
        }
        aclService.getAccessControlByObjectIdAndGrouseUserOrThrow(
                objectId, getUser());
    }
}
