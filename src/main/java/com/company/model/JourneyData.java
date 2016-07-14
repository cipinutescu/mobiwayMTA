package com.company.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ciprian on 6/7/2016.
 */

@Entity
@Table(name = "journey_data",catalog = "mobiway_db")
public class JourneyData {

    private Integer id;
    private Integer journey_id;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    private Timestamp timestamp;
    private String osm_way_id;

    public JourneyData() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id",nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "journey_id",nullable = false)
    public Integer getJourney_id() {
        return journey_id;
    }

    public void setJourney_id(Integer journey_id) {
        this.journey_id = journey_id;
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

    @Column(name = "osm_way_id")
    public String getOsm_way_id() {
        return osm_way_id;
    }

    public void setOsm_way_id(String osm_way_id) {
        this.osm_way_id = osm_way_id;
    }

    @Override
    public String toString() {
        return "JourneyData{" +
                "id=" + id +
                ", journey_id=" + journey_id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", timestamp=" + timestamp +
                ", osm_way_id='" + osm_way_id + '\'' +
                '}';
    }
}
