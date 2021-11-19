package myapp.ebank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users",
        indexes = {
                @Index(name = "date_index", columnList = "createdDate"),
                @Index(name = "isActive_index", columnList = "isActive")})
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
    @Column(name = "cnic", unique = true, nullable = false)
    @NotBlank(message = "cnic is mandatory")
    private String cnic;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "password is mandatory")
    private String password;
    @Column(name = "age")
    @Min(value = 1, message = "age should be greater than 0")
    @Digits(integer = 3, fraction = 0)
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

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organizations organization;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(targetEntity = Roles.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "roles_id", nullable = false)})
    private List<Roles> roles = new ArrayList<>();


}
