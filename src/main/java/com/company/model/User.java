package com.company.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by Ciprian on 6/6/2016.
 */

@Entity
@Table(name = "user", catalog = "mobiway_db")
public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String facebookToken;
    private Integer facebookTokenExpiresIn;
    private String authToken;
    private Integer authTokenExpiresIn;
    private String uuid;

    public User(){}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "facebook_token")
    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    @Column(name = "facebook_expires_in")
    public Integer getFacebookTokenExpiresIn() {
        return facebookTokenExpiresIn;
    }

    public void setFacebookTokenExpiresIn(Integer facebookTokenExpiresIn) {
        this.facebookTokenExpiresIn = facebookTokenExpiresIn;
    }

    @Column(name = "auth_token",nullable = false)
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Column(name = "auth_expires_in",nullable = false)
    public Integer getAuthTokenExpiresIn() {
        return authTokenExpiresIn;
    }

    public void setAuthTokenExpiresIn(Integer authTokenExpiresIn) {
        this.authTokenExpiresIn = authTokenExpiresIn;
    }

    @Column(name = "uuid",nullable = false)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", facebookToken='" + facebookToken + '\'' +
                ", facebookTokenExpiresIn=" + facebookTokenExpiresIn +
                ", authToken='" + authToken + '\'' +
                ", authTokenExpiresIn=" + authTokenExpiresIn +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
