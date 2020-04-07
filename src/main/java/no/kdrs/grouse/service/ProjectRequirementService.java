package no.kdrs.grouse.service;

import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.persistence.IProjectRequirementRepository;
import no.kdrs.grouse.service.interfaces.IProjectRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by tsodring on 9/25/17.
 */
@Service
@Transactional
public class ProjectRequirementService
        extends GrouseService
        implements IProjectRequirementService {

    private EntityManager em;
    private IProjectRequirementRepository projectRequirementRepository;

    // Columns that it is possible to update via a PATCH request
    private ArrayList<String> allowableColumns =
            new ArrayList<>(Arrays.asList("requirementText", "showOrder",
                    "ownedBy", "priority", "requirementNumber"));

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
     * <p>
     * [
     * { "op": "replace", "path": "/requirementText", "value": "hello"},
     * ]
     * <p>
     * For the sake of this application "replace" is the only
     * operation we support. For this application this is acceptable.
     * <p>
     * Probably should log no change occurring, or throw an Exception is the
     * entity does not exist in the database
     * <p>
     * This approach results in four calls to the database.
     * 1. Check the entity exists
     * 2. Update query
     * clear context and
     * 3. Get the updated object and return to caller
     * <p>
     * This is probably an anti-pattern
     *
     * @param patchObjects      All the pathObjects contained in one object
     * @param requirementNumber The id of row to change
     * @return The newly persisted ProjectRequirement
     * @throws Exception if it can't handle the syntax for some reason
     */
    @Override
    public ProjectRequirement updateProjectRequirement(
            PatchObjects patchObjects, Long requirementNumber) {
        return (ProjectRequirement)
                handlePatch(getProjectRequirementOrThrow(requirementNumber),
                        patchObjects);
    }

    @Override
    public ProjectRequirement createProjectRequirement(
            UUID projectId, String functionality,
            ProjectRequirement projectRequirement) {

        return projectRequirementRepository.save(projectRequirement);
    }

    @Override
    protected boolean checkColumnUpdatable(String path) {
        return allowableColumns.contains(path);
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
        ProjectRequirement projectRequirement = projectRequirementRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No Project exists with Id " + id));
        checkAccess(projectRequirement.getReferenceProject().getProjectId());
        return projectRequirement;
    }
}
