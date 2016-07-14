package com.company.dao;

import com.company.HibernateUtil;
import com.company.model.Journey;
import com.company.model.JourneyData;
import com.company.model.Location;
import com.company.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Ciprian on 6/7/2016.
 */
public class LocationDao {

    public static final Double NORD_LAT = 44.464;
    public static final Double NORD_LONG = 22.096;

    public static final Double EST_LAT = 44.403;
    public static final Double EST_LONG = 22.166;

    public static final Double SUD_LAT = 44.386;
    public static final Double SUD_LONG = 26.124;

    public static final Double VEST_LAT = 44.429;
    public static final Double VEST_LONG = 26.671;

    public static final Double CENTER_LAT = 44.432;
    public static final Double CENTER_LONG = 26.098;
    public static final Long SECONDS_IN_YEAR = 3600 * 24 * 366L;

    public static final Double RAZA = 0.046;


    public static boolean addNewLocation(Location location){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(location);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public static boolean addNewDummyLocation(Integer user_id){
        Location location = new Location();
        location.setUser_id(user_id);
        location.setLatitude(Math.random() % 2 == 0 ?
                CENTER_LAT + Math.random() * RAZA:
                CENTER_LAT + Math.random() * RAZA);
        location.setLongitude(Math.random() % 2 == 0 ?
                CENTER_LONG + Math.random() * RAZA :
                CENTER_LONG + Math.random() * RAZA);
        location.setSpeed((int) (100 * Math.random()));
        Random random = new Random(100);
        location.setTimestamp(new Timestamp(System.currentTimeMillis()- ((long) (Math.random() * SECONDS_IN_YEAR))));
        return addNewLocation(location);
    }

    public static Location getLastUserLocation(Integer user_id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List locationList = null;
        try {
            tx = session.beginTransaction();
            locationList =  session.createQuery("FROM Location l where l.user_id=" + user_id ).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (Location) locationList.get(0);
    }
}
