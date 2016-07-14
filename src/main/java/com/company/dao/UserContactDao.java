package com.company.dao;

import com.company.HibernateUtil;
import com.company.model.JourneyData;
import com.company.model.User;
import com.company.model.UserContact;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 6/7/2016.
 */
public class UserContactDao {
    public static boolean createUserContact(UserContact userContact){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(userContact);
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


    public static List<User> getAllFriendsByUsername(String username){
        User user = UserDao.getUserByUsername(username).get(0);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> userFriends = new ArrayList<User>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<UserContact> userContacts =  (List<UserContact>)session.createQuery("FROM UserContact uc where uc.id_user="+ user.getId()).list();
            tx.commit();
            for(UserContact userContact: userContacts){
                userFriends.add(UserDao.getUserById(userContact.getId_friend_user()));
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userFriends;
    }
}
