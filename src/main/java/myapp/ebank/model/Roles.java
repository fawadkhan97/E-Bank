package myapp.ebank.model;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Roles implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ManyToMany(targetEntity = Permissions.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "roles_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {
            @JoinColumn(name = "permission_id")})
    private List<Permissions> permissions = new ArrayList<>();


}
