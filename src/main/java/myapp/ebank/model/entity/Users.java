package myapp.ebank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Data
public class Users implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "fullName", nullable = false)
    @NotBlank(message = "fullName is mandatory")
    private String fullName;
    @Column(name = "userName", unique = true, nullable = false)
    @NotBlank(message = "username is mandatory")
    private String userName;
    @Column(name = "PhoneNumber", unique = true, nullable = false)
    @NotBlank(message = "phoneNumber is mandatory")
    private String phoneNumber;
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "email is mandatory")
    private String email;
    @Column(name = "CNIC", unique = true, nullable = false)
    @NotBlank(message = "cnic is mandatory")
    private String cnic;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "password is mandatory")
    private String password;
    @Column(name = "age")
    private int age;
    @Column(name = "dob")
    private String dob;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "isActive")
    private boolean isActive;
    @Column(name = "token")
    private int token;

    @OneToMany(targetEntity = Funds.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<Funds> funds = new ArrayList<>();

    @OneToMany(targetEntity = Loans.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<Loans> loans = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "organization_id",nullable = false)
    private Organizations organization;

    @ManyToMany(targetEntity = Roles.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id",nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "roles_id",nullable = false)})
    private List<Roles> roles = new ArrayList<>();


}
