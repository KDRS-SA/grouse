package no.kdrs.grouse.service.interfaces;

import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.utils.PatchObjects;

/**
 * Created by tsodring on 29/03/18.
 */
public interface IProjectRequirementService {
    ProjectRequirement getProjectRequirement(Long id);
    void deleteProjectRequirement(Long requirementNumber);

    void deleteRequirementByObject(ProjectRequirement projectRequirement);

    ProjectRequirement updateProjectRequirement(
            PatchObjects patchObjects, Long requirementNumber) throws Exception;

    ProjectRequirement createProjectRequirement(
            Long projectId,String functionality,
            ProjectRequirement projectRequirement);
}
