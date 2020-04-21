package no.kdrs.grouse.service.interfaces;

import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.utils.PatchObjects;

public interface ITemplateRequirementService {

    TemplateRequirement findById(Long id);

    TemplateRequirement updateTemplateRequirement(
            PatchObjects patchObjects, Long requirementNumber);

    void delete(Long id);
}
