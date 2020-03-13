package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.TemplateRequirementController;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.model.links.LinksTemplateRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
