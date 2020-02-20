package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.utils.PatchObjects;

import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
public interface ITemplateRequirementService {
    List<TemplateRequirement> findAll();

    TemplateRequirement findById(Long id);

    TemplateRequirement save(TemplateRequirement templateRequirement);

    TemplateRequirement updateTemplateRequirement(
            PatchObjects patchObjects, Long requirementNumber);

    void delete(Long id);
}
