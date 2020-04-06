package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.ACLController;
import no.kdrs.grouse.model.AccessControl;
import no.kdrs.grouse.model.links.LinksAccessControl;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ACLAssembler
        extends RepresentationModelAssemblerSupport<
        AccessControl, LinksAccessControl> {

    public ACLAssembler() {
        super(ACLController.class, LinksAccessControl.class);
    }

    @Override
    public LinksAccessControl toModel(AccessControl accessControl) {
        LinksAccessControl linksAccessControl = instantiateModel(accessControl);

        linksAccessControl.setAclId(accessControl.getAclId());
        linksAccessControl.setGrouseObject(accessControl.getGrouseObject());
        linksAccessControl.setGrouseUser(accessControl.getGrouseUser());
        linksAccessControl.setCreatedDate(accessControl.getCreatedDate());
        linksAccessControl.setLastModifiedDate(
                accessControl.getLastModifiedDate());

        linksAccessControl.add(linkTo(methodOn(ACLController.class)
                .getACLEntry(accessControl.getAclId()))
                .withSelfRel());
        return linksAccessControl;
    }
}
