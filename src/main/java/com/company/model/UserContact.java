package com.company.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ciprian on 6/7/2016.
 */
@Entity
@Table(name = "user_contact", catalog = "mobiway_db")
public class UserContact {
    private Integer id;
    private Integer id_user;
    private Integer id_friend_user;

    public UserContact(){}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "id_user",nullable = false)
    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    @Column(name = "id_friend_user",nullable = false)
    public Integer getId_friend_user() {
        return id_friend_user;
    }

    public void setId_friend_user(Integer id_friend_user) {
        this.id_friend_user = id_friend_user;
    }

    @Override
    public String toString() {
        return "UserContact{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", id_friend_user=" + id_friend_user +
                '}';
    }
}
