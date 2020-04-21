package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.TemplateController;
import no.kdrs.grouse.controller.TemplateFunctionalityController;
import no.kdrs.grouse.controller.TemplateRequirementController;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.model.links.LinksTemplateRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.utils.Constants.REL_PARENT_FUNCTIONALITY;
import static no.kdrs.grouse.utils.Constants.TEMPLATE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TemplateRequirementAssembler
        extends RepresentationModelAssemblerSupport
        <TemplateRequirement, LinksTemplateRequirement> {

    public TemplateRequirementAssembler() {
        super(TemplateRequirementController.class,
                LinksTemplateRequirement.class);
    }

    @Override
    public LinksTemplateRequirement toModel(
            TemplateRequirement templateRequirement) {

        LinksTemplateRequirement linksTemplateRequirement =
                instantiateModel(templateRequirement);

        linksTemplateRequirement.setRequirementId(
                templateRequirement.getRequirementId());
        linksTemplateRequirement.setRequirementText(
                templateRequirement.getRequirementText());
        linksTemplateRequirement.setRequirementNumber(
                templateRequirement.getRequirementNumber());
        linksTemplateRequirement.setRequirementType(
                templateRequirement.getRequirementType());
        linksTemplateRequirement.setShowOrder(
                templateRequirement.getShowOrder());
        linksTemplateRequirement.setPriority(templateRequirement.getPriority());
        linksTemplateRequirement.setRequirement(
                templateRequirement.getRequirement());

        if (null != templateRequirement.getReferenceFunctionality()) {
            linksTemplateRequirement
                    .add(linkTo(methodOn(TemplateFunctionalityController.class)
                            .getTemplateFunctionality(templateRequirement
                                    .getReferenceFunctionality()
                                    .getFunctionalityId()))
                            .withRel(REL_PARENT_FUNCTIONALITY));
        }
        linksTemplateRequirement
                .add(linkTo(methodOn(TemplateController.class)
                        .getTemplate(templateRequirement
                                .getReferenceTemplate().getTemplateId()))
                        .withRel(TEMPLATE));

        linksTemplateRequirement.add(
                linkTo(methodOn(TemplateRequirementController.class)
                        .getRequirement(templateRequirement.getRequirementId()))
                        .withSelfRel());
        return linksTemplateRequirement;
    }

    @Override
    public CollectionModel<LinksTemplateRequirement> toCollectionModel(
            Iterable<? extends TemplateRequirement> templateRequirements) {
        CollectionModel<LinksTemplateRequirement> linksTemplateRequirements =
                super.toCollectionModel(templateRequirements);

        linksTemplateRequirements.forEach(templateRequirement -> {
            templateRequirement
                    .add(linkTo(methodOn(TemplateRequirementController.class)
                            .getRequirement(
                                    templateRequirement.getRequirementId()))
                            .withSelfRel());
        });
        return linksTemplateRequirements;
    }
}
