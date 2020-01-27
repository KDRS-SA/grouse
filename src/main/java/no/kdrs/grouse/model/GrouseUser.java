package no.kdrs.grouse.model;

import no.kdrs.grouse.model.user.Authority;
import org.springframework.hateoas.RepresentationModel;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
@Table(name = "user")
public class GrouseUser
        extends RepresentationModel {

    @Id
    @Email
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_non_expired")
    Boolean accountNonExpired = true;

    @Column(name = "credentials_non_expired")
    Boolean credentialsNonExpired = true;

    @Column(name = "account_non_locked")
    Boolean accountNonLocked = true;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "account_created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "username", referencedColumnName = "username")},
            inverseJoinColumns = {@JoinColumn(name = "authority", referencedColumnName = "authority_name")})
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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



    @Override
    public String toString() {
        return "GrouseUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", createdDate=" + createdDate +
                ", enabled=" + enabled +
                '}';
    }
}
