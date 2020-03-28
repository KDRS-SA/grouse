package no.kdrs.grouse.service;

import no.kdrs.grouse.model.AccessControl;
import no.kdrs.grouse.persistence.IACLRepository;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ACLService
        extends GrouseService {

    private IACLRepository aclRepository;

    public ACLService(IACLRepository aclRepository) {
        this.aclRepository = aclRepository;
    }

    public AccessControl getACLEntry(UUID objectId) {
        AccessControl accessControl = getAccessControlOrThrow(objectId);
        if (accessControl.getGrouseUser().equals(getUser())) {
            return accessControl;
        }
        throw new AccessDeniedException("No access for object: " + objectId);
    }

    public AccessControl createACLEntry(
            UUID objectId,
            AccessControl incomingAccessControl) {
        AccessControl accessControl = getAccessControlOrThrow(objectId);
        if (accessControl.getGrouseUser().equals(getUser())) {
            return aclRepository.save(incomingAccessControl);
        }
        throw new AccessDeniedException("No access for object: " + objectId);
    }

    /**
     * @param objectId
     */
    public void deleteACLEntry(UUID objectId) {
        AccessControl accessControl = getAccessControlOrThrow(objectId);
        if (accessControl.getGrouseUser().equals(getUser())) {
            aclRepository.delete(accessControl);
        }
        throw new AccessDeniedException("No access for object: " + objectId);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid AccessControl back. If there is no
     * valid AccessControl, a EntityNotFoundException exception is thrown
     *
     * @param id The systemId of the accessControl object to retrieve
     * @return the accessControl object
     */
    private AccessControl getAccessControlOrThrow(@NotNull UUID id)
            throws EntityNotFoundException {
        return aclRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No AccessControl exists with Id " + id));
    }

}
