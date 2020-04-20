package no.kdrs.grouse.service;

import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.persistence.IProjectRequirementRepository;
import no.kdrs.grouse.service.interfaces.IProjectRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Transactional
public class ProjectRequirementService
        extends GrouseService
        implements IProjectRequirementService {

    private final IProjectRequirementRepository projectRequirementRepository;

    // Columns that it is possible to update via a PATCH request
    private final ArrayList<String> allowableColumns =
            new ArrayList<>(Arrays.asList("requirementText", "showOrder",
                    "ownedBy", "priority", "requirementNumber"));

    public ProjectRequirementService(
            IProjectRequirementRepository projectRequirementRepository) {
        this.projectRequirementRepository = projectRequirementRepository;
    }

    @Override
    public void deleteProjectRequirement(Long requirementNumber) {
        getProjectRequirementOrThrow(requirementNumber);
        projectRequirementRepository.deleteById(requirementNumber);
    }

    @Override
    public void deleteRequirementByObject(
            ProjectRequirement projectRequirement) {
        projectRequirementRepository.delete(projectRequirement);
    }

    @Override
    public ProjectRequirement getProjectRequirement(Long id) {
        return getProjectRequirementOrThrow(id);
    }

    @Override
    public ProjectRequirement updateProjectRequirement(
            PatchObjects patchObjects, Long requirementNumber) {
        return (ProjectRequirement)
                handlePatch(getProjectRequirementOrThrow(requirementNumber),
                        patchObjects);
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
     * thrown.
     * <p>
     * Note this method will only return the object if you access to it. If
     * you do not have access an exception is thrown.
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
                                "No ProjectRequirement exists with Id " + id));
        checkAccess(projectRequirement.getReferenceProject().getProjectId());
        return projectRequirement;
    }
}
