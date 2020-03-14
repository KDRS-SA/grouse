package no.kdrs.grouse.service;

import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.persistence.IProjectFunctionalityRepository;
import no.kdrs.grouse.persistence.IProjectRequirementRepository;
import no.kdrs.grouse.service.interfaces.IProjectFunctionalityService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by tsodring on 01/04/18.
 */
@Service
@Transactional
public class ProjectFunctionalityService
        extends GrouseService
        implements IProjectFunctionalityService {

    private IProjectRequirementRepository projectRequirementRepository;
    private IProjectFunctionalityRepository projectFunctionalityRepository;

    // Columns that it is possible to update via a PATCH request
    private ArrayList<String> allowableColumns =
            new ArrayList<>(Arrays.asList("title", "processed", "ownedBy"));

    public ProjectFunctionalityService(IProjectRequirementRepository
                                               projectRequirementRepository,
                                       IProjectFunctionalityRepository
                                               projectFunctionalityRepository) {
        this.projectRequirementRepository = projectRequirementRepository;
        this.projectFunctionalityRepository = projectFunctionalityRepository;
    }

    @Override
    public ProjectFunctionality getProjectFunctionality(
            Long projectFunctionalityId) {
        return getProjectFunctionalityOrThrow(projectFunctionalityId);
    }

    @Override
    public Page<ProjectFunctionality> getChildFunctionality(
            Pageable pageable, Long projectFunctionalityId) {
        return projectFunctionalityRepository
                .findByReferenceParentFunctionality(pageable,
                        getProjectFunctionalityOrThrow(projectFunctionalityId));
    }

    @Override
    public Page<ProjectRequirement> getRequirements(
            Pageable pageable, Long projectFunctionalityId) {
        return projectRequirementRepository
                .findByReferenceFunctionality(
                        getProjectFunctionalityOrThrow(projectFunctionalityId),
                        pageable);
    }

    @Override
    public ProjectRequirement createProjectRequirement(
            Long projectFunctionalityId, ProjectRequirement projectRequirement) {

        Optional<ProjectFunctionality> projectFunctionality =
                projectFunctionalityRepository.findById(projectFunctionalityId);
        if (projectFunctionality.isPresent()) {
            String loggedInUser = SecurityContextHolder.getContext().getAuthentication()
                    .getName();
            projectRequirement.setOwnedBy(loggedInUser);
            projectRequirement.setReferenceFunctionality(
                    projectFunctionality.get());
        } else {
            throw new EntityNotFoundException(
                    "Cannot find ProjectFunctionality [" +
                            projectFunctionalityId + "]");
        }

        return projectRequirementRepository.save(projectRequirement);
    }


    @Override
    public ProjectFunctionality updateProjectFunctionality(
            PatchObjects patchObjects, Long requirementNumber) {
        return (ProjectFunctionality)
                handlePatch(getProjectFunctionalityOrThrow(requirementNumber),
                        patchObjects);
    }

    @Override
    public void delete(Long functionalityID) {
        projectFunctionalityRepository.deleteById(functionalityID);
    }

    @Override
    protected boolean checkColumnUpdatable(String path) {
        return allowableColumns.contains(path);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid Project back. If there is no valid
     * ProjectRequirement, a EntityNotFoundException exception is thrown
     * <p>
     * We also check that you only can access things you own. If you try
     * to access an object you don't own, then a AccessDeniedException is thrown.
     *
     * @param id The id  of the ProjectRequirement object to retrieve
     * @return the ProjectRequirement object
     */
    private ProjectFunctionality getProjectFunctionalityOrThrow(@NotNull Long id)
            throws EntityNotFoundException, AccessDeniedException {
        ProjectFunctionality projectFunctionality = projectFunctionalityRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No ProjectRequirement exists with Id " + id));

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        if (!projectFunctionality.getOwnedBy().equals(loggedInUser)) {
            throw new AccessDeniedException("Du er pålogget med en bruker som ikke har tilgang" +
                    " til dette prosjekt funksjonalitet!");
        }
        return projectFunctionality;
    }
}
