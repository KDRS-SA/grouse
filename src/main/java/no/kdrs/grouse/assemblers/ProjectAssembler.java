package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.DocumentController;
import no.kdrs.grouse.controller.ProjectController;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.links.LinksProject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.utils.Constants.*;
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
        linksProject.setDocumentCreated(project.getDocumentCreated());
        linksProject.setProjectComplete(project.getProjectComplete());
        linksProject.setPercentForDocument(project.getPercentForDocument());
        linksProject.add(linkTo(methodOn(ProjectController.class).
                getProject(project.getProjectId()))
                .withSelfRel());
        linksProject.add(linkTo(methodOn(ProjectController.class).
                getFunctionalityForProject(null,
                        project.getProjectId()))
                .withRel(FUNCTIONALITY));
        linksProject.add(linkTo(methodOn(DocumentController.class)
                .downloadProjectDocument(null, project.getProjectId()))
                .withRel(DOCUMENT));
        linksProject.add(linkTo(methodOn(DocumentController.class)
                .getSupportedFileFormats())
                .withRel(SUPPORTED_FORMATS));
        linksProject.add(linkTo(methodOn(ProjectController.class)
                .shareProject(project.getProjectId(),
                        USER_EMAIL_ADDRESS))
                .withRel(SHARE));
        linksProject.add(linkTo(methodOn(ProjectController.class)
                .getProjectUsers(project.getProjectId(), null))
                .withRel(ACCESS));
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
                                    null, project.getProjectId()))
                            .withRel(FUNCTIONALITY));
            project.add(linkTo(methodOn(DocumentController.class)
                    .downloadProjectDocument(null, project.getProjectId()))
                    .withRel(DOCUMENT));
            project.add(linkTo(methodOn(DocumentController.class)
                    .getSupportedFileFormats())
                    .withRel(SUPPORTED_FORMATS));
            project.add(linkTo(methodOn(ProjectController.class)
                    .shareProject(project.getProjectId(),
                            "{user email address}"))
                    .withRel(SHARE));
            project.add(linkTo(methodOn(ProjectController.class)
                    .getProjectUsers(project.getProjectId(), null))
                    .withRel(ACCESS));
        });
        return linksProjects;
    }
}
