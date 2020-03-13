package no.kdrs.grouse.service;

import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.persistence.IProjectFunctionalityRepository;
import no.kdrs.grouse.persistence.IProjectRequirementRepository;
import no.kdrs.grouse.service.interfaces.IProjectFunctionalityService;
import no.kdrs.grouse.utils.PatchObject;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by tsodring on 01/04/18.
 */
@Service
@Transactional
public class ProjectFunctionalityService
        implements IProjectFunctionalityService {

    private EntityManager em;
    private IProjectRequirementRepository projectRequirementRepository;
    private IProjectFunctionalityRepository projectFunctionalityRepository;

    public ProjectFunctionalityService(EntityManager em,
                                       IProjectRequirementRepository
                                               projectRequirementRepository,
                                       IProjectFunctionalityRepository
                                               projectFunctionalityRepository) {
        this.em = em;
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
            PatchObjects patchObjects, Long requirementNumber)
            throws Exception {

        // If the entity does not exist, throw an Exception
        getProjectFunctionalityOrThrow(requirementNumber);

        for (PatchObject patchObject : patchObjects.getPatchObjects()) {
            if ("replace".equalsIgnoreCase(patchObject.getOp())
                    && null != patchObject.getPath()
                    && null != patchObject.getValue()) {
                String path = patchObject.getPath();
                if ("/".equals(path.substring(0, 1))) {
                    path = path.substring(1);
                }

                String updateQuery = "update ProjectFunctionality set "
                        + path + " = :value where id = :id";
                Query query = em.createQuery(updateQuery);
                query.setParameter("value", Boolean.valueOf(patchObject
                        .getValue()));
                query.setParameter("id", requirementNumber);
                query.executeUpdate();
            } else {
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
        return em.find(ProjectFunctionality.class, requirementNumber);
    }


    @Override
    public void delete(Long functionalityID) {
        projectFunctionalityRepository.deleteById(functionalityID);
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
