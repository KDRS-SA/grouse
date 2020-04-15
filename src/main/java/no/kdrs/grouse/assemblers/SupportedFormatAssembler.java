package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.DocumentController;
import no.kdrs.grouse.model.SupportedFormats;
import no.kdrs.grouse.model.links.LinksSupportedFormats;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SupportedFormatAssembler
        extends RepresentationModelAssemblerSupport<
        SupportedFormats, LinksSupportedFormats> {

    public SupportedFormatAssembler() {
        super(DocumentController.class, LinksSupportedFormats.class);
    }

    @Override
    public LinksSupportedFormats toModel(SupportedFormats supportedFormats) {
        LinksSupportedFormats linksSupportedFormats = instantiateModel(supportedFormats);
        linksSupportedFormats.setSupportedFormats(supportedFormats);
        linksSupportedFormats.add(linkTo(methodOn(DocumentController.class)
                .getSupportedFileFormats())
                .withSelfRel());
        return linksSupportedFormats;
    }

    @Override
    public CollectionModel<LinksSupportedFormats> toCollectionModel(
            Iterable<? extends SupportedFormats> supportedFormats) {
        CollectionModel<LinksSupportedFormats> linksSupportedFormats =
                super.toCollectionModel(supportedFormats);

        linksSupportedFormats.forEach(supportedFormat -> {
            supportedFormat
                    .add(linkTo(methodOn(DocumentController.class)
                            .getSupportedFileFormats())
                            .withSelfRel());
        });
        return linksSupportedFormats;
    }
}
