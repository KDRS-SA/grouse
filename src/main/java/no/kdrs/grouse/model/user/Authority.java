package no.kdrs.grouse.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.kdrs.grouse.model.GrouseUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "authority_name", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<GrouseUser> users;

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public List<GrouseUser> getUsers() {
        return users;
    }

    public void setUsers(List<GrouseUser> users) {
        this.users = users;
    }
}
