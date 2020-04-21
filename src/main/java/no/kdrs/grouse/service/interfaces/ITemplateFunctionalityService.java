package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITemplateFunctionalityService {

    TemplateFunctionality getTemplateFunctionality(Long id);

    Page<TemplateFunctionality> getChildFunctionality(
            Pageable pageable, Long templateFunctionalityId);

    Page<TemplateRequirement> getRequirements(
            Pageable pageable, Long templateFunctionalityId);

    TemplateRequirement createTemplateRequirement(
            Long functionalityNumber, TemplateRequirement templateRequirement);

    TemplateFunctionality createChildFunctionality(
            Long templateFunctionalityId,
            TemplateFunctionality incomingFunctionality);

    TemplateFunctionality updateTemplateFunctionality(PatchObjects patchObjects,
                                                      Long functionalityNumber)
            throws Exception;

    void delete(Long functionalityNumber);
}
