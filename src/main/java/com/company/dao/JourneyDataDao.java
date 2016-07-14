package com.company.dao;

import com.company.HibernateUtil;
import com.company.model.Journey;
import com.company.model.JourneyData;
import com.company.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Ciprian on 6/7/2016.
 */
public class JourneyDataDao {
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

    public static boolean addNewJourneyData(JourneyData journeyData){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(journeyData);
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

    public static boolean deleteAllJourneyData(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from JourneyData";
            Query query = session.createQuery(hql);
            query.executeUpdate();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    public static boolean generateDummyJourneyData(Integer jouney_id){
        JourneyData journeyData = new JourneyData();
        journeyData.setJourney_id(jouney_id);
        journeyData.setLatitude(Math.random() % 2 == 0 ?
                CENTER_LAT + Math.random() * RAZA:
                CENTER_LAT + Math.random() * RAZA);
        journeyData.setLongitude(Math.random() % 2 == 0 ?
                CENTER_LONG + Math.random() * RAZA :
                CENTER_LONG + Math.random() * RAZA);
        journeyData.setSpeed((int) (100 * Math.random()));
        Random random = new Random(100);
        journeyData.setTimestamp(new Timestamp(System.currentTimeMillis()- ((long) (Math.random() * SECONDS_IN_YEAR))));
        journeyData.setOsm_way_id(UUID.randomUUID().toString());
        return addNewJourneyData(journeyData);
    }

    public static List<JourneyData> getAllJourneyDataByJourneyName(String journeyName){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> userList = new ArrayList<User>();
        Transaction tx = null;
        List journeyDataList = null;
        try {
            tx = session.beginTransaction();
            Journey journey = (Journey) session.createQuery("FROM Journey j where j.journeyName='" + journeyName + "'").list().get(0);
            tx.commit();
            tx = session.beginTransaction();
            journeyDataList = session.createQuery("FROM JourneyData jd where jd.journey_id=" +journey.getId()+"").list();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return journeyDataList;
    }

    public static List<JourneyData> getAllJourneyData(String username){
        List<Journey> journeis = JourneyDao.getJourneyByUsername(username);
        List<JourneyData> journeyDataList = new ArrayList<JourneyData>();
        for(Journey journey : journeis){
            journeyDataList.addAll(getAllJourneyDataByJourneyName(journey.getJourneyName()));
        }
        return journeyDataList;
    }

    public static List<JourneyData> getFullJourneyDataForAllUsers(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List journeyDataList = null;
        try {
            tx = session.beginTransaction();
            journeyDataList = session.createQuery("FROM JourneyData jd").list();
        } catch (HibernateException e){
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  journeyDataList;
    }


    public static List<List<JourneyData>> getFullJourneyDataForAllUsersByTravel(String username){
        List<List<JourneyData>> bigData = new ArrayList<>();
        List<Journey> journeyList = JourneyDao.getJourneyByUsername(username);
        for(Journey journey :  journeyList){
            bigData.add(JourneyDataDao.getAllJourneyDataByJourneyName(journey.getJourneyName()));
        }
        return  bigData;
    }

    public static JourneyData getLastKnowPosition(String username){
        List<JourneyData> journeyDataList = getAllJourneyData(username);
        JourneyData lastJourneyData = journeyDataList.get(0);
        for (JourneyData jd : journeyDataList){
            if(jd.getTimestamp().after(lastJourneyData.getTimestamp()))
                lastJourneyData = jd;
        }
        return lastJourneyData;
    }
}
