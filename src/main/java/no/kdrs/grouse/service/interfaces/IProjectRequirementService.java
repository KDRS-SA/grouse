package no.kdrs.grouse.service.interfaces;

import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.utils.PatchObjects;

public interface IProjectRequirementService {
    ProjectRequirement getProjectRequirement(Long id);

    void deleteProjectRequirement(Long requirementNumber);

    void deleteRequirementByObject(ProjectRequirement projectRequirement);

    ProjectRequirement updateProjectRequirement(
            PatchObjects patchObjects, Long requirementNumber) throws Exception;
}
