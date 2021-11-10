package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "CreatedDate")
    private Date CreatedDate;
    @Column(name = "UpdatedDate")
    private Date UpdatedDate;
    private boolean isActive;

    @ManyToMany(targetEntity = Permissions.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "roles_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {
            @JoinColumn(name = "permission_id")})
    private List<Permissions> permissions = new ArrayList<>();
}

