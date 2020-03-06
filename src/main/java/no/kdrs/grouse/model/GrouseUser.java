package no.kdrs.grouse.model;

import no.kdrs.grouse.model.user.Authority;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

/**
 * Created by tsodring 28/03/2018
 * <p>
 * Basic GrouseUser entity to define a user that can login into the system. It is
 * assumed that the username is an email address.
 * <p>
 * Called GrouseUser as spring has a GrouseUser entity define somewhere and I always
 * find it messy having both of these being used in the same method.
 */
@Entity
@Table(name = USER_TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class GrouseUser
        extends RepresentationModel<GrouseUser> {

    @Id
    @Email
    @NotNull
    @Column(name = USERNAME, nullable = false)
    private String username;

    @NotNull
    @Column(name = PASSWORD, nullable = false)
    private String password;

    @Column(name = ACCOUNT_NON_EXPIRED)
    private Boolean accountNonExpired = true;

    @Column(name = CREDENTIALS_NON_EXPIRED)
    private Boolean credentialsNonExpired = true;

    @Column(name = ACCOUNT_NON_LOCKED)
    private Boolean accountNonLocked = true;

    @Column(name = ENABLED)
    private Boolean enabled = true;

    @CreatedDate
    @Column(name = CREATED_DATE, updatable = false)
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime createdDate;

    @Version
    @Column(name = VERSION)
    private Long version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = USER_AUTHORITY_JOIN,
            joinColumns = {
                    @JoinColumn(name = USERNAME,
                            referencedColumnName = USERNAME)},
            inverseJoinColumns =
                    {@JoinColumn(name = AUTHORITY,
                            referencedColumnName = AUTHORITY_NAME)})
    private List<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "GrouseUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", enabled=" + enabled +
                ", createdDate=" + createdDate +
                ", version=" + version +
                '}';
    }
}
