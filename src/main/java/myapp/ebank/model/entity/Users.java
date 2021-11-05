package myapp.ebank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
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
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "userName", unique = true)
    private String userName;
    @Column(name = "PhoneNumber", unique = true)
    private String phoneNumber;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "CNIC", unique = true)
    private String CNIC;
    @Column(name = "password")
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
    @JoinColumn
    private List<Funds> funds = new ArrayList<>();

    @OneToMany(targetEntity = Loans.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn
    private List<Loans> loans = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn
    private Organizations organization;

    @ManyToMany(targetEntity = Roles.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "roles_id")})
    private List<Roles> roles = new ArrayList<>();


}
