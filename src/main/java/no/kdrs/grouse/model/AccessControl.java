package no.kdrs.grouse.model;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Entity
@Table(name = ACL_TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class AccessControl {

    /**
     * Primary key. Set to UUID as grouse will gradually move over to UUID
     * rather than integer primary keys.
     */
    @Id
    @Column(name = ACL_PK_ID, updatable = false)
    @Type(type = "uuid-char")
    private UUID aclId;

    /**
     * UUID of the grouse object this entry is applicable for. We are using
     * UUID so that we do not care about the table/entity the grouse object is
     * applicable to. Consequence is that you cannot do a reverse lookup of a
     * UUID in grouse and find out which table it is in.
     */
    @Column(name = GROUSE_OBJECT)
    @Type(type = "uuid-char")
    private UUID grouseObject;

    @Column(name = GROUSE_USER)
    private String grouseUser;

    @Column(name = OBJECT_TYPE)
    private String objectType;

    /**
     * The date the entry was created
     */
    @CreatedDate
    @Column(name = CREATED_DATE, updatable = false)
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime createdDate;

    /**
     * The date the entry was accessed
     */
    @LastModifiedDate
    @Column(name = CHANGED_DATE)
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime lastModifiedDate;

    @CreatedBy
    @NotNull
    @Column(name = "ownedBy")
    private String ownedBy;

    @Version
    @Column(name = VERSION)
    private Long version;

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

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getOwnedBy() {
        return ownedBy;
    }


    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "AccessControl{" +
                "aclId=" + aclId +
                ", grouseObject=" + grouseObject +
                ", grouseUser='" + grouseUser + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", version=" + version +
                '}';
    }

    public static final class AccessControlBuilder {
        private UUID grouseObject;
        private String grouseUser;

        private AccessControlBuilder() {
        }

        public static AccessControlBuilder anAccessControl() {
            return new AccessControlBuilder();
        }

        public AccessControlBuilder withGrouseObject(UUID grouseObject) {
            this.grouseObject = grouseObject;
            return this;
        }

        public AccessControlBuilder withGrouseUser(String grouseUser) {
            this.grouseUser = grouseUser;
            return this;
        }

        public AccessControl build() {
            AccessControl accessControl = new AccessControl();
            accessControl.setGrouseObject(grouseObject);
            accessControl.setGrouseUser(grouseUser);
            accessControl.setAclId(randomUUID());
            return accessControl;
        }
    }
}
