package myapp.ebank.model;

import javax.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Users {
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
    @Column(name = "userType")
    private String userType;
    @Column(name = "age")
    private int age;
    @Column(name = "dob")
    private String dob;
    @Column(name = "CreatedDate")
    private String CreatedDate;
    @Column(name = "UpdatedDate")
    private String UpdatedDate;
    @Column(name = "isActive")
    private boolean isActive;
    @Column(name = "Token")
    private int token;



    @OneToMany(targetEntity = Funds.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Funds> funds = new ArrayList<>();

    @OneToMany(targetEntity = Loans.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Loans> loans = new ArrayList<>();

    @ManyToOne(targetEntity = Organizations.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private Organizations organization;

    @ManyToMany(targetEntity = Roles.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "roles_id")})
    private List<Roles> roles = new ArrayList<>();


}
