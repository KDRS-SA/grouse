package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.ProjectController;
import no.kdrs.grouse.controller.TemplateController;
import no.kdrs.grouse.controller.UserController;
import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.links.LinksUser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.utils.Constants.PROJECT;
import static no.kdrs.grouse.utils.Constants.TEMPLATE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler
        extends RepresentationModelAssemblerSupport<GrouseUser, LinksUser> {

    public UserAssembler() {
        super(UserController.class, LinksUser.class);
    }

    @Override
    public LinksUser toModel(GrouseUser user) {
        LinksUser linksUser = instantiateModel(user);

        linksUser.setUsername(user.getUsername());
        linksUser.setAccountNonExpired(user.isAccountNonExpired());
        linksUser.setAccountNonLocked(user.isAccountNonExpired());
        linksUser.setCreatedDate(user.getCreatedDate());
        linksUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
        linksUser.setEnabled(user.isEnabled());

        linksUser.add(linkTo(methodOn(UserController.class)
                .getGrouseUser(user.getUsername()))
                .withSelfRel())
                .add(linkTo(methodOn(ProjectController.class)
                        .createProject(null))
                        .withRel(PROJECT))
                .add(linkTo(methodOn(TemplateController.class)
                        .createTemplate(null))
                        .withRel(TEMPLATE));

        return linksUser;
    }

    @Override
    public CollectionModel<LinksUser> toCollectionModel(
            Iterable<? extends GrouseUser> users) {
        CollectionModel<LinksUser> linksUsers =
                super.toCollectionModel(users);

        linksUsers.forEach(user -> {
            user
                    .add(linkTo(methodOn(UserController.class)
                            .getGrouseUser(user.getUsername()))
                            .withSelfRel())
                    .add(linkTo(methodOn(ProjectController.class)
                            .createProject(null))
                            .withRel(PROJECT))
                    .add(linkTo(methodOn(TemplateController.class)
                            .createTemplate(null))
                            .withRel(TEMPLATE));
        });
        return linksUsers;
    }
}
