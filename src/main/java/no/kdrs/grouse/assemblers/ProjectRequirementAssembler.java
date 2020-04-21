package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.ProjectController;
import no.kdrs.grouse.controller.ProjectFunctionalityController;
import no.kdrs.grouse.controller.ProjectRequirementController;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.model.links.LinksProjectRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.utils.Constants.PROJECT;
import static no.kdrs.grouse.utils.Constants.REL_PARENT_FUNCTIONALITY;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectRequirementAssembler
        extends RepresentationModelAssemblerSupport
        <ProjectRequirement, LinksProjectRequirement> {

    public ProjectRequirementAssembler() {
        super(ProjectRequirementController.class,
                LinksProjectRequirement.class);
    }

    @Override
    public LinksProjectRequirement toModel(
            ProjectRequirement projectRequirement) {

        LinksProjectRequirement linksProjectRequirement =
                instantiateModel(projectRequirement);

        linksProjectRequirement.setRequirementId(
                projectRequirement.getRequirementId());
        linksProjectRequirement.setRequirementText(
                projectRequirement.getRequirementText());
        linksProjectRequirement.setRequirementNumber(
                projectRequirement.getRequirementNumber());
        linksProjectRequirement.setRequirementType(
                projectRequirement.getRequirementType());
        linksProjectRequirement.setShowOrder(
                projectRequirement.getShowOrder());
        linksProjectRequirement.setPriority(projectRequirement.getPriority());
        linksProjectRequirement.setRequirement(
                projectRequirement.getRequirement());

        if (null != projectRequirement.getReferenceFunctionality()) {
            linksProjectRequirement
                    .add(linkTo(methodOn(ProjectFunctionalityController.class)
                            .getProjectFunctionality(projectRequirement
                                    .getReferenceFunctionality()
                                    .getProjectFunctionalityId()))
                            .withRel(REL_PARENT_FUNCTIONALITY));
        }
        linksProjectRequirement
                .add(linkTo(methodOn(ProjectController.class)
                        .getProject(projectRequirement
                                .getReferenceProject().getProjectId()))
                        .withRel(PROJECT));
        linksProjectRequirement.add(
                linkTo(methodOn(ProjectRequirementController.class)
                        .getRequirement(projectRequirement.getRequirementId()))
                        .withSelfRel());
        return linksProjectRequirement;
    }

    @Override
    public CollectionModel<LinksProjectRequirement> toCollectionModel(
            Iterable<? extends ProjectRequirement> projectRequirements) {
        CollectionModel<LinksProjectRequirement> linksProjectRequirements =
                super.toCollectionModel(projectRequirements);

        linksProjectRequirements.forEach(projectRequirement -> {
            projectRequirement
                    .add(linkTo(methodOn(ProjectRequirementController.class)
                            .getRequirement(
                                    projectRequirement.getRequirementId()))
                            .withSelfRel());
        });
        return linksProjectRequirements;
    }
}
