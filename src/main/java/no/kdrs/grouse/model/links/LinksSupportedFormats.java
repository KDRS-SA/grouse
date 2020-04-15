package no.kdrs.grouse.model.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import no.kdrs.grouse.model.SupportedFormats;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode(callSuper = false)
@Data
@Relation(collectionRelation = "supportedFormats",
        itemRelation = "supportedFormat")
@JsonInclude(NON_NULL)
public class LinksSupportedFormats
        extends RepresentationModel<LinksSupportedFormats> {

    private Map<String, String> supportedFormats;

    public LinksSupportedFormats() {
    }

    public LinksSupportedFormats(SupportedFormats supportedFormats) {
        this.supportedFormats = supportedFormats.getSupportedFormats();
    }

    public Map<String, String> getSupportedFormats() {
        return supportedFormats;
    }

    public void setSupportedFormats(SupportedFormats supportedFormats) {
        this.supportedFormats = supportedFormats.getSupportedFormats();
    }
}
