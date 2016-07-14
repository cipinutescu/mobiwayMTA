package com.company.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Ciprian on 6/9/2016.
 */
public class LocationUser {

    private String user_name;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    private Timestamp timestamp;

    public LocationUser() {
    }


    @Id
    @Column(name = "id_user",nullable = false)
    public String getUser_id() {
        return user_name;
    }

    public void setUser_id(String user_name) {
        this.user_name = user_name;
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

    @Override
    public String toString() {
        return "Location{" +
                "user_id=" + user_name +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", timestamp=" + timestamp +
                '}';
    }
}
