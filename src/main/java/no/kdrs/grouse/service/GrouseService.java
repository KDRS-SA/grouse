package no.kdrs.grouse.service;

import no.kdrs.grouse.utils.PatchObject;
import no.kdrs.grouse.utils.PatchObjects;
import no.kdrs.grouse.utils.exception.InternalException;
import no.kdrs.grouse.utils.exception.PatchMisconfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class GrouseService {

    private static final Logger logger =
            LoggerFactory.getLogger(TemplateRequirementService.class);

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
            if ("replace" .equalsIgnoreCase(patchObject.getOp())
                    && null != patchObject.getPath()
                    && patchObject.getPath().length() > 2
                    && null != patchObject.getValue()) {
                String path = patchObject.getPath();
                if ("/" .equals(path.substring(0, 1))) {
                    path = path.substring(1);
                }
                String methodName = "set" + path.substring(0, 1)
                        .toUpperCase() + path.substring(1);
                try {
                    Method method = object.getClass()
                            .getMethod(methodName, String.class);
                    method.invoke(object, patchObject.getValue());
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
}
