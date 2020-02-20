package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ITemplateService {
    List<Template> findAll();

    Template findById(Long id);

    Template createTemplate(Template template);

    Template update(Long id, Template template) throws Exception;

    void delete(Long id);

    List<Template> findByOwnedBy(String ownedBy);

    List<TemplateRequirement> findByTemplateIdOrderByTemplateName(
            Long templateId, String functionalityNumber);

    List<TemplateFunctionality> findFunctionalityForTemplateByType(
            Long templateId, String type);

    void createFunctionality(@NotNull final Long templateId,
                             @NotNull TemplateFunctionality
                                     templateFunctionality);
}
