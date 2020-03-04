package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.UserController;
import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.links.LinksUser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
                .withSelfRel());

        //linksUser.add(linkTo(methodOn(ProjectController.class)
        //        .createProject()
        //        .withRel(FUNCTIONALITY));
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
                            .withSelfRel());
          /*  user
                    .add(linkTo(methodOn(UserController.class)
                            .getFunctionalityForUser(
                                    user.getUserId()))
                            .withRel(FUNCTIONALITY));
                            */
        });
        return linksUsers;
    }
}
