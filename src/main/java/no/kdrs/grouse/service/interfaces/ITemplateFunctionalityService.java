package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.utils.PatchObjects;

import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
public interface ITemplateFunctionalityService {
    List<TemplateFunctionality> findAll();

    TemplateFunctionality findById(Long id);

    TemplateFunctionality findByFunctionalityNumber(String functionalityNumber);

    TemplateFunctionality save(TemplateFunctionality templateFunctionality);

    TemplateFunctionality updateProjectFunctionality(
            PatchObjects patchObjects, Long requirementNumber);

    void delete(Long id);

    List<TemplateFunctionality> findByShowMeAndReferenceParentFunctionality(
            Boolean menuItem, String parent);

    void createTemplateRequirement(Long templateFunctionalityId,
                                   TemplateRequirement templateRequirement);
}
