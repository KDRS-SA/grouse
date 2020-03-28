package no.kdrs.grouse.model.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import no.kdrs.grouse.model.AccessControl;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "accessControls", itemRelation = "accessControl")
@JsonInclude(NON_NULL)
public class LinksAccessControl
        extends RepresentationModel<LinksAccessControl> {

    private UUID aclId;
    private UUID grouseObject;
    private String grouseUser;
    private Boolean read;
    private Boolean update;
    private Boolean delete;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private Long version;

    public LinksAccessControl(AccessControl accessControl) {
        this.aclId = accessControl.getAclId();
        this.grouseObject = accessControl.getGrouseObject();
        this.grouseUser = accessControl.getGrouseUser();
        this.read = accessControl.canRead();
        this.update = accessControl.canUpdate();
        this.delete = accessControl.canDelete();
        this.createdDate = accessControl.getCreatedDate();
        this.lastModifiedDate = accessControl.getLastModifiedDate();
        this.version = accessControl.getVersion();
    }

    public UUID getAclId() {
        return aclId;
    }

    public void setAclId(UUID aclId) {
        this.aclId = aclId;
    }

    public UUID getGrouseObject() {
        return grouseObject;
    }

    public void setGrouseObject(UUID grouseObject) {
        this.grouseObject = grouseObject;
    }

    public String getGrouseUser() {
        return grouseUser;
    }

    public void setGrouseUser(String grouseUser) {
        this.grouseUser = grouseUser;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
