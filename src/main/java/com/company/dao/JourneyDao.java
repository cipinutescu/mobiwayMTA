package com.company.dao;

import com.company.HibernateUtil;
import com.company.model.Journey;
import com.company.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ciprian on 6/7/2016.
 */
public class JourneyDao {


    public static List<Journey> getJourneyByUsername(String username){
        User user = UserDao.getUserByUsername(username).get(0);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Journey> journeyList = new ArrayList<Journey>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List journeys = session.createQuery("FROM Journey j where j.idUser=" + user.getId() + "").list();
            for (Iterator iterator =
                 journeys.iterator(); iterator.hasNext(); ) {
                journeyList.add((Journey) iterator.next());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return journeyList;
    }

    public static boolean addNewJourney(Journey journey){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(journey);
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

    public static boolean addDummyJourney(Integer user_id){
        Journey journey = new Journey();
        journey.setIdUser(user_id);
        journey.setJourneyName(UUID.randomUUID().toString());
        return addNewJourney(journey);
    }

    public static boolean deleteAllJourney(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from Journey";
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
}
