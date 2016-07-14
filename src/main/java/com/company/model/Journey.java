package com.company.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ciprian on 6/7/2016.
 */
@Entity
@Table(name = "journey",catalog = "mobiway_db")
public class Journey {
    private Integer id;
    private Integer idUser;
    private String journeyName;

    public Journey(){}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id",nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "id_user",nullable = false)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Column(name = "journey_name",nullable = false)
    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", journeyName='" + journeyName + '\'' +
                '}';
    }
}
