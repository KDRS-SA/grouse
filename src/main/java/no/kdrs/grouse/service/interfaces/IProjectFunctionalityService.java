package no.kdrs.grouse.service.interfaces;

import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.utils.PatchObjects;

/**
 * Created by tsodring on 01/04/18.
 */
public interface IProjectFunctionalityService {
    ProjectFunctionality getProjectFunctionality(Long id);
    ProjectRequirement createProjectRequirement(
            Long functionalityNumber, ProjectRequirement projectRequirement);
    ProjectFunctionality updateProjectFunctionality(PatchObjects patchObjects,
                                                     Long functionalityNumber)
            throws Exception;
    void delete(Long functionalityNumber);
}
