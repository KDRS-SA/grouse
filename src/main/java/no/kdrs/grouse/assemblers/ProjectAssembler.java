package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.ProjectController;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.links.LinksProject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.utils.Constants.FUNCTIONALITY;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectAssembler
        extends RepresentationModelAssemblerSupport<Project, LinksProject> {

    public ProjectAssembler() {
        super(ProjectController.class, LinksProject.class);
    }

    @Override
    public LinksProject toModel(Project project) {
        LinksProject linksProject = instantiateModel(project);
        linksProject.setProjectId(project.getProjectId());
        linksProject.setProjectName(project.getProjectName());
        linksProject.setOwnedBy(project.getOwnedBy());
        linksProject.setCreatedDate(project.getCreatedDate());
        linksProject.setLastModifiedDate(project.getLastModifidDate());
        linksProject.setPercentForDocument(project.getPercentForDocument());
        linksProject.add(linkTo(methodOn(ProjectController.class).
                getProject(project.getProjectId())).withSelfRel());
        linksProject.add(linkTo(methodOn(ProjectController.class).
                getFunctionalityForProject(project.getProjectId()))
                .withRel(FUNCTIONALITY));
        return linksProject;
    }

    @Override
    public CollectionModel<LinksProject> toCollectionModel(
            Iterable<? extends Project> projects) {
        CollectionModel<LinksProject> linksProjects =
                super.toCollectionModel(projects);

        linksProjects.forEach(project -> {
            project
                    .add(linkTo(methodOn(ProjectController.class)
                            .getProject(project.getProjectId()))
                            .withSelfRel());
            project
                    .add(linkTo(methodOn(ProjectController.class)
                            .getFunctionalityForProject(
                                    project.getProjectId()))
                            .withRel(FUNCTIONALITY));
        });
        return linksProjects;
    }
}
