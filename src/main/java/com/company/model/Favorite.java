package com.company.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ciprian on 6/7/2016.
 */
@Entity
@Table(name = "favorite",catalog = "mobiway_db")
public class Favorite {
    private Integer loc_id;
    private Integer user_id;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    private Timestamp timestamp;
    private String description;

    public Favorite() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "loc_id",unique = true, nullable = false)
    public Integer getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(Integer loc_id) {
        this.loc_id = loc_id;
    }

    @Column(name = "id_user",nullable = false)
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "speed")
    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }



    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Favorite{" +
                "loc_id=" + loc_id +
                ", user_id=" + user_id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                '}';
    }
}
