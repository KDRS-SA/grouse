package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.ProjectFunctionalityController;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.links.LinksProjectFunctionality;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.utils.Constants.FUNCTIONALITY;
import static no.kdrs.grouse.utils.Constants.REQUIREMENT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectFunctionalityAssembler
        extends RepresentationModelAssemblerSupport
        <ProjectFunctionality, LinksProjectFunctionality> {

    public ProjectFunctionalityAssembler() {
        super(ProjectFunctionalityController.class,
                LinksProjectFunctionality.class);
    }

    @Override
    public LinksProjectFunctionality toModel(
            ProjectFunctionality projectFunctionality) {
        LinksProjectFunctionality linksProjectFunctionality =
                instantiateModel(projectFunctionality);
        copyValues(linksProjectFunctionality, projectFunctionality);
        if (projectFunctionality
                .getReferenceChildProjectFunctionality()
                .size() > 0) {
            linksProjectFunctionality
                    .add(linkTo(methodOn(ProjectFunctionalityController.class)
                            .getProjectFunctionality(linksProjectFunctionality
                                    .getProjectFunctionalityId()))
                            .slash(FUNCTIONALITY)
                            .withRel(FUNCTIONALITY));
        }
        if (projectFunctionality
                .getReferenceProjectRequirement()
                .size() > 0) {
            linksProjectFunctionality
                    .add(linkTo(methodOn(ProjectFunctionalityController.class)
                            .getProjectRequirements(null, projectFunctionality
                                    .getProjectFunctionalityId()))
                            .slash(REQUIREMENT)
                            .withRel(REQUIREMENT));
        }
        linksProjectFunctionality
                .add(linkTo(methodOn(ProjectFunctionalityController.class)
                        .getProjectFunctionality(projectFunctionality
                                .getProjectFunctionalityId()))
                        .withSelfRel());

        return linksProjectFunctionality;
    }

    @Override
    public CollectionModel<LinksProjectFunctionality> toCollectionModel(
            Iterable<? extends ProjectFunctionality> projectFunctionalitys) {

        CollectionModel<LinksProjectFunctionality> linksProjectFunctionalitys =
                super.toCollectionModel(projectFunctionalitys);

        linksProjectFunctionalitys.forEach(linksProjectFunctionality -> {

            if (linksProjectFunctionality.hasFunctionality()) {
                linksProjectFunctionality
                        .add(linkTo(methodOn(
                                ProjectFunctionalityController.class)
                                .getProjectFunctionality(
                                        linksProjectFunctionality
                                                .getProjectFunctionalityId()))
                                .slash(FUNCTIONALITY)
                                .withRel(FUNCTIONALITY));
            }
            if (linksProjectFunctionality.hasRequirements()) {
                linksProjectFunctionality
                        .add(linkTo(
                                methodOn(ProjectFunctionalityController.class)
                                        .getProjectRequirements(null,
                                                linksProjectFunctionality
                                                        .getProjectFunctionalityId()))
                                .slash(REQUIREMENT)
                                .withRel(REQUIREMENT));
            }
            linksProjectFunctionality
                    .add(linkTo(methodOn(ProjectFunctionalityController.class)
                            .getProjectFunctionality(linksProjectFunctionality
                                    .getProjectFunctionalityId()))
                            .withSelfRel());
        });
        return linksProjectFunctionalitys;
    }

    private void copyValues(
            LinksProjectFunctionality linksProjectFunctionality,
            ProjectFunctionality projectFunctionality) {
        linksProjectFunctionality.setProjectFunctionalityId(projectFunctionality
                .getProjectFunctionalityId());
        linksProjectFunctionality.setFunctionalityNumber(projectFunctionality
                .getFunctionalityNumber());
        linksProjectFunctionality.setTitle(projectFunctionality.getTitle());
        linksProjectFunctionality.setDescription(
                projectFunctionality.getDescription());
        linksProjectFunctionality.setConsequence(
                projectFunctionality.getConsequence());
        linksProjectFunctionality.setExplanation(
                projectFunctionality.getExplanation());
        linksProjectFunctionality.setShowMe(projectFunctionality.getShowMe());
        linksProjectFunctionality.setProcessed(
                projectFunctionality.getProcessed());
        linksProjectFunctionality.setActive(projectFunctionality.getActive());
        linksProjectFunctionality.setType(projectFunctionality.getType());
        linksProjectFunctionality.setOwnedBy(projectFunctionality.getOwnedBy());
        linksProjectFunctionality.setVersion(projectFunctionality.getVersion());
    }
}
