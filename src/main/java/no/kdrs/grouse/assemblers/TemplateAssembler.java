package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.TemplateController;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.links.LinksTemplate;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.utils.Constants.FUNCTIONALITY;
import static no.kdrs.grouse.utils.Constants.PROJECT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TemplateAssembler
        extends RepresentationModelAssemblerSupport<Template, LinksTemplate> {

    public TemplateAssembler() {
        super(TemplateController.class, LinksTemplate.class);
    }

    @Override
    public LinksTemplate toModel(Template template) {
        LinksTemplate linksTemplate = instantiateModel(template);

        linksTemplate.setTemplateId(template.getTemplateId());
        linksTemplate.setTemplateName(template.getTemplateName());
        linksTemplate.setOwnedBy(template.getOwnedBy());
        linksTemplate.setCreatedDate(template.getCreatedDate());
        linksTemplate.setLastModifiedDate(template.getLastModifiedDate());

        linksTemplate.add(linkTo(methodOn(TemplateController.class)
                .getTemplate(template.getTemplateId())).withSelfRel());
        linksTemplate.add(linkTo(methodOn(TemplateController.class)
                .getFunctionalityForTemplate(template.getTemplateId()))
                .withRel(FUNCTIONALITY));
        linksTemplate.add(linkTo(methodOn(TemplateController.class)
                .createProjectFromTemplate(template.getTemplateId(), null))
                .withRel(PROJECT));
        return linksTemplate;
    }

    @Override
    public CollectionModel<LinksTemplate> toCollectionModel(
            Iterable<? extends Template> templates) {
        CollectionModel<LinksTemplate> linksTemplates =
                super.toCollectionModel(templates);

        linksTemplates.forEach(template -> {
            template
                    .add(linkTo(methodOn(TemplateController.class)
                            .getTemplate(template.getTemplateId()))
                            .withSelfRel());
            template
                    .add(linkTo(methodOn(TemplateController.class)
                            .getFunctionalityForTemplate(
                                    template.getTemplateId()))
                            .withRel(FUNCTIONALITY));
        });
        return linksTemplates;
    }
}
