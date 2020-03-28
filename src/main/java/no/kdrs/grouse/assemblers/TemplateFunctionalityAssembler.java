package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.TemplateFunctionalityController;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.links.LinksTemplateFunctionality;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.utils.Constants.FUNCTIONALITY;
import static no.kdrs.grouse.utils.Constants.REQUIREMENT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TemplateFunctionalityAssembler
        extends RepresentationModelAssemblerSupport
        <TemplateFunctionality, LinksTemplateFunctionality> {

    public TemplateFunctionalityAssembler() {
        super(TemplateFunctionalityController.class,
                LinksTemplateFunctionality.class);
    }

    @Override
    public LinksTemplateFunctionality toModel(
            TemplateFunctionality templateFunctionality) {
        LinksTemplateFunctionality linksTemplateFunctionality =
                instantiateModel(templateFunctionality);
        copyValues(linksTemplateFunctionality, templateFunctionality);
        if (templateFunctionality
                .getReferenceChildTemplateFunctionality()
                .size() > 0) {
            linksTemplateFunctionality
                    .add(linkTo(methodOn(TemplateFunctionalityController.class)
                            .getTemplateChildFunctionality(null,
                                    linksTemplateFunctionality
                                            .getTemplateFunctionalityId()))
                            .withRel(FUNCTIONALITY));
        }
        if (templateFunctionality
                .getReferenceTemplateRequirement()
                .size() > 0) {
            linksTemplateFunctionality
                    .add(linkTo(methodOn(TemplateFunctionalityController.class)
                            .getTemplateRequirements(null, templateFunctionality
                                    .getFunctionalityId()))
                            .withRel(REQUIREMENT));
        }
        linksTemplateFunctionality
                .add(linkTo(methodOn(TemplateFunctionalityController.class)
                        .getTemplateFunctionality(templateFunctionality
                                .getFunctionalityId()))
                        .withSelfRel());

        return linksTemplateFunctionality;
    }

    @Override
    public CollectionModel<LinksTemplateFunctionality> toCollectionModel(
            Iterable<? extends TemplateFunctionality> templateFunctionalitys) {

        CollectionModel<LinksTemplateFunctionality> linksTemplateFunctionalitys =
                super.toCollectionModel(templateFunctionalitys);

        linksTemplateFunctionalitys.forEach(linksTemplateFunctionality -> {

            if (linksTemplateFunctionality.hasFunctionality()) {
                linksTemplateFunctionality
                        .add(linkTo(methodOn(
                                TemplateFunctionalityController.class)
                                .getTemplateChildFunctionality(null,
                                        linksTemplateFunctionality
                                                .getTemplateFunctionalityId()))
                                .withRel(FUNCTIONALITY));
            }
            if (linksTemplateFunctionality.hasRequirements()) {
                linksTemplateFunctionality
                        .add(linkTo(
                                methodOn(TemplateFunctionalityController.class)
                                        .getTemplateRequirements(null,
                                                linksTemplateFunctionality
                                                        .getTemplateFunctionalityId()))
                                .withRel(REQUIREMENT));
            }
            linksTemplateFunctionality
                    .add(linkTo(methodOn(TemplateFunctionalityController.class)
                            .getTemplateFunctionality(linksTemplateFunctionality
                                    .getTemplateFunctionalityId()))
                            .withSelfRel());
        });
        return linksTemplateFunctionalitys;
    }

    private void copyValues(
            LinksTemplateFunctionality linksTemplateFunctionality,
            TemplateFunctionality templateFunctionality) {
        linksTemplateFunctionality.setTemplateFunctionalityId(
                templateFunctionality.getFunctionalityId());
        linksTemplateFunctionality.setFunctionalityNumber(templateFunctionality
                .getFunctionalityNumber());
        linksTemplateFunctionality.setTitle(templateFunctionality.getTitle());
        linksTemplateFunctionality.setDescription(
                templateFunctionality.getDescription());
        linksTemplateFunctionality.setConsequence(
                templateFunctionality.getConsequence());
        linksTemplateFunctionality.setExplanation(
                templateFunctionality.getExplanation());
        linksTemplateFunctionality.setShowMe(templateFunctionality.getShowMe());
        linksTemplateFunctionality.setType(templateFunctionality.getType());
        linksTemplateFunctionality.setOwnedBy(templateFunctionality.getOwnedBy());
        linksTemplateFunctionality.setVersion(templateFunctionality.getVersion());
    }
}
