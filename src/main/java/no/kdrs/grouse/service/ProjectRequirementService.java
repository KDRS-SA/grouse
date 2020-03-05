package no.kdrs.grouse.service;

import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.persistence.IProjectRequirementRepository;
import no.kdrs.grouse.service.interfaces.IProjectRequirementService;
import no.kdrs.grouse.utils.PatchObject;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

/**
 * Created by tsodring on 9/25/17.
 */
@Service
@Transactional
public class ProjectRequirementService
        implements IProjectRequirementService {

    private EntityManager em;
    private IProjectRequirementRepository projectRequirementRepository;

    public ProjectRequirementService(
            EntityManager em,
            IProjectRequirementRepository projectRequirementRepository) {
        this.em = em;
        this.projectRequirementRepository = projectRequirementRepository;
    }

    @Override
    public void deleteProjectRequirement(Long requirementNumber) {
        // Just make sure the projectRequirement exists AND we
        // are its owner!
        getProjectRequirementOrThrow(requirementNumber);
        projectRequirementRepository.deleteById(requirementNumber);
    }

    public void deleteRequirementByObject(
            ProjectRequirement projectRequirement) {
        projectRequirementRepository.delete(projectRequirement);
    }

    @Override
    public ProjectRequirement getProjectRequirement(Long id) {
        return getProjectRequirementOrThrow(id);
    }

    /**
     * Just testing out how to implement PATCH (RFC 6902)
     *
     * [
     *  { "op": "replace", "path": "/requirementText", "value": "hello"},
     * ]
     *
     * For the sake of this application "replace" is the only
     * operation we support. For this application this is acceptable.
     *
     * Probably should log no change occurring, or throw an Exception is the
     * entity does not exist in the database
     *
     * This approach results in four calls to the database.
     *  1. Check the entity exists
     *  2. Update query
     *    clear context and
     *  3. Get the updated object and return to caller
     *
     * This is probably an anti-pattern
     *
     * @param patchObjects All the pathObjects contained in one object
     * @param requirementNumber The id of row to change
     * @return The newly persisted ProjectRequirement
     * @throws Exception if it can't handle the syntax for some reason
     */
    @Override
    public ProjectRequirement updateProjectRequirement(
            PatchObjects patchObjects, Long requirementNumber)
            throws Exception {

        // If the entity does not exist, or we don't own the object throw an Exception
        getProjectRequirementOrThrow(requirementNumber);

        for (PatchObject patchObject: patchObjects.getPatchObjects()) {
            if("replace".equalsIgnoreCase(patchObject.getOp())
                    && null != patchObject.getPath()
                    && null != patchObject.getValue()) {
                String path = patchObject.getPath();
                if ("/".equals(path.substring(0,1))) {
                    path = path.substring(1);
                }

                String updateQuery = "update ProjectRequirement set "
                        + path + " = :value where id = :id";
                Query query = em.createQuery(updateQuery);
                query.setParameter("value", patchObject.getValue());
                query.setParameter("id", requirementNumber);
                query.executeUpdate();
            }
            else {
                throw new Exception("Cannot handle this PatchObject " +
                                     patchObject.toString());
            }
        }
        // persist changes to database as there may have been multiple
        // updates
        em.flush();
        // clear the context so we can retrieve the newly persisted object
        em.clear();

        // reread the projectRequirement as there may have been multiple
        // changes. Not sure if this is needed or not.
        return em.find(ProjectRequirement.class, requirementNumber);
    }

    @Override
    public ProjectRequirement createProjectRequirement(
            Long projectId, String functionality,
            ProjectRequirement projectRequirement) {

        return projectRequirementRepository.save(projectRequirement);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid ProjectRequirement back. If there is
     * no valid ProjectRequirement, a EntityNotFoundException exception is
     * thrown
     *
     * @param id The id of the projectRequirement object to retrieve
     * @return the projectRequirement object
     */
    private ProjectRequirement getProjectRequirementOrThrow(@NotNull Long id)
            throws EntityNotFoundException {
        ProjectRequirement projectRequirement = projectRequirementRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No Project exists with Id " + id));

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        if (!projectRequirement.getOwnedBy().equals(loggedInUser)) {
            throw new AccessDeniedException("Du er pålogget med en bruker som ikke har tilgang" +
                    " til dette prosjekt kravet!");
        }

        return projectRequirement;
    }
}
