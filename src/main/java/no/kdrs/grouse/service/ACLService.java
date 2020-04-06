package no.kdrs.grouse.service;

import no.kdrs.grouse.model.AccessControl;
import no.kdrs.grouse.persistence.IACLRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@Transactional
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
        // Check that there isn't already an access control set
        Optional<AccessControl> accessControlOpt =
                getAccessControlByObjectIdAndGrouseUser(
                        objectId, incomingAccessControl.getGrouseUser());

        if (accessControlOpt.isEmpty()) {
            AccessControl accessControl = getAccessControlByObjectId(objectId);
            if (accessControl.getGrouseUser().equals(getUser())) {
                return aclRepository.save(incomingAccessControl);
            }
            throw new AccessDeniedException("No access for object: " +
                    objectId);
        } else {
            return accessControlOpt.get();
        }

    }

    public AccessControl createACLEntryOnCreate(
            UUID objectId,
            AccessControl incomingAccessControl) {
        incomingAccessControl.setAclId(randomUUID());
        return aclRepository.save(incomingAccessControl);
    }

    public List<UUID> getListUUIDs(String ownedBy, String objectType) {
        List<AccessControl> accessControlList = aclRepository
                .findByGrouseUserAndObjectType(ownedBy, objectType);
        List<UUID> uuidList = new ArrayList<>(accessControlList.size());
        for (AccessControl accessControl : accessControlList) {
            uuidList.add(accessControl.getGrouseObject());
        }
        return uuidList;
    }

    public List<String> getListUsers(UUID projectId) {
        List<AccessControl> accessControlList = aclRepository
                .findByGrouseObject(projectId);
        List<String> userList = new ArrayList<>(accessControlList.size());
        for (AccessControl accessControl : accessControlList) {
            userList.add(accessControl.getGrouseUser());
        }
        return userList;
    }

    public void deleteACLEntry(UUID objectId, String username) {
        AccessControl accessControl =
                getAccessControlByObjectIdAndGrouseUserOrThrow(objectId, username);
        // A user can delete their own share
        if (accessControl.getGrouseUser().equals(getUser())) {
            aclRepository.delete(accessControl);
            return;
        }
        // An owner can delete anybodys share
        if (accessControl.getOwnedBy().equals(getUser())) {
            aclRepository.delete(accessControl);
            return;
        }
        throw new AccessDeniedException("No access for object: " + objectId);
    }

    public void deleteACLEntry(UUID aclId) {
        AccessControl accessControl =
                getAccessControlOrThrow(aclId);
        if (accessControl.getGrouseUser().equals(getUser())) {
            aclRepository.delete(accessControl);
            return;
        }
        throw new AccessDeniedException("No access for object: " + aclId);
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

    public AccessControl getAccessControlByObjectIdAndGrouseUserOrThrow(
            @NotNull UUID objectId, @NotNull String username) {
        return aclRepository.findFirstByGrouseUserAndGrouseObject(
                username, objectId)
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "No access to ObjectId " + objectId));
    }

    private Optional<AccessControl> getAccessControlByObjectIdAndGrouseUser(
            @NotNull UUID objectId, @NotNull String username) {
        return aclRepository.findFirstByGrouseUserAndGrouseObject(
                username, objectId);
    }


    private AccessControl getAccessControlByObjectId(@NotNull UUID objectId)
            throws EntityNotFoundException {
        return aclRepository.findFirstByOwnedByAndGrouseObject(
                getUser(), objectId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No AccessControl exists with ObjectId " +
                                        objectId));
    }

}
