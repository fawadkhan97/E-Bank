package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private Date CreatedDate;
    private Date UpdatedDate;
    private boolean isActive;

    @ManyToMany(targetEntity = Permissions.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "roles_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {
            @JoinColumn(name = "permission_id")})
    private List<Permissions> permissions = new ArrayList<>();
}

