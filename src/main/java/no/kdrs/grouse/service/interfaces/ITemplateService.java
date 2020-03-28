package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface ITemplateService {

    Page<Template> findAll(Pageable page);

    Template findById(UUID id);

    Page<TemplateFunctionality> findFunctionalityForTemplate(
            Pageable pageable, UUID templateId);

    Template createTemplate(Template template);

    Template update(UUID id, PatchObjects patchObjects) throws Exception;

    void delete(UUID id);

    List<Template> findByOwnedBy(String ownedBy);

    void createFunctionality(@NotNull final UUID templateId,
                             @NotNull TemplateFunctionality
                                     templateFunctionality);
}
