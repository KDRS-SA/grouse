package no.kdrs.grouse.service;

import no.kdrs.grouse.utils.PatchObject;
import no.kdrs.grouse.utils.PatchObjects;
import no.kdrs.grouse.utils.exception.BadRequestException;
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

    protected Object handlePatch(Object object, PatchObjects patchObjects) {
        for (PatchObject patchObject : patchObjects.getPatchObjects()) {
            if ("replace".equalsIgnoreCase(patchObject.getOp())
                    && null != patchObject.getPath()
                    && patchObject.getPath().length() > 2
                    && null != patchObject.getValue()) {
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
                    // If the variable (path) you are trying to update is a
                    // password then you have to encode the new password
                    if (PASSWORD.equalsIgnoreCase(path)) {
                        setMethod.invoke(object,
                                encoder.encode(patchObject.getValue()
                                        .toString()));
                    } else {
                        setMethod.invoke(object, patchObject.getValue());
                    }
                } catch (SecurityException | NoSuchMethodException |
                        IllegalArgumentException | IllegalAccessException |
                        InvocationTargetException e) {
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
        return parseETAG(((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest().getHeader(ETAG));
    }

    /**
     * Retrieve the etag value but without the quotes. An ETAG should be
     * quoted e.g. "0"
     *
     * @param quotedETAG the incoming etag from the header
     * @return the etag value without quotes
     */
    private Long parseETAG(String quotedETAG) {
        long etagVal = -1L;
        if (quotedETAG != null) {
            try {
                etagVal = Long.parseLong(
                        quotedETAG.replaceAll("^\"|\"$", ""));
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
        if (!getUser().equals(ownedBy)) {
            String error = NO_ACCESS_OBJECT + objectType;
            logger.error(error);
            throw new AccessDeniedException(error);
        }
    }

    public void checkAccess(UUID objectId) {
        aclService.getAccessControlByObjectIdAndGrouseUserOrThrow(
                objectId, getUser());
    }
}
