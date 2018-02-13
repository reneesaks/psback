package com.perfectstrangers.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.perfectstrangers.domain.enums.Gender;
import com.perfectstrangers.util.PasswordHasher;
import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * JsonIgnore must be set on the password and getter. Setter must have JsonProperty in order to ignore it only
 * on get methods.
 */

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "alias")
    private String alias;

    @NotNull
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "activated")
    private boolean activated = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "age")
    private String age;

    @Column(name = "reg_date", columnDefinition = "DATETIME")
    private Instant regDate;

    @Column(name = "lastVisit")
    private String lastVisit;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "user_degree",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "degree_id")
    )
    private List<Degree> degree;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "user_occupation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "occupation_id")
    )
    private List<Occupation> occupation;

    /**
     * Roles are being eagerly loaded here because they are a fairly small collection of items for this
     * example.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "user_advert",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "advert_id")
    )
    private List<Advert> adverts;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "user_response",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "response_id")
    )
    private List<Response> responses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * Password is being encrypted upon setting password.
     *
     * @param password password in raw form
     */
    @JsonProperty
    public void setPassword(String password) {
        this.password = new PasswordHasher().hashPasswordWithSha256(password);
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Instant getRegDate() {
        return regDate;
    }

    public void setRegDate(Instant regDate) {
        this.regDate = regDate;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public List<Degree> getDegree() {
        return degree;
    }

    public void setDegree(List<Degree> degree) {
        this.degree = degree;
    }

    public List<Occupation> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<Occupation> occupation) {
        this.occupation = occupation;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}

