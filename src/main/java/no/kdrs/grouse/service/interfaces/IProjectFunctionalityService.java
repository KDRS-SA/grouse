package no.kdrs.grouse.service.interfaces;

import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by tsodring on 01/04/18.
 */
public interface IProjectFunctionalityService {
    ProjectFunctionality getProjectFunctionality(Long id);

    Page<ProjectFunctionality> getChildFunctionality(
            Pageable pageable, Long projectFunctionalityId);

    ProjectRequirement createProjectRequirement(
            Long functionalityNumber, ProjectRequirement projectRequirement);

    ProjectFunctionality updateProjectFunctionality(PatchObjects patchObjects,
                                                    Long functionalityNumber)
            throws Exception;

    void delete(Long functionalityNumber);
}
