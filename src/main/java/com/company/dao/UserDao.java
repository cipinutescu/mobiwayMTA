package com.company.dao;

import com.company.HibernateUtil;
import com.company.model.User;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ciprian on 6/6/2016.
 */
public class UserDao {
    public static boolean createDummyUser(String username){
        User user = new User();
        user.setUsername(username);
        user.setPassword(UUID.randomUUID().toString());
        user.setFirstname(UUID.randomUUID().toString());
        user.setLastname(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString().substring(0,14));
        user.setFacebookToken(UUID.randomUUID().toString());
        user.setFacebookTokenExpiresIn((int) (Math.random() * 1000));
        user.setAuthToken(UUID.randomUUID().toString());
        user.setAuthTokenExpiresIn((int) (Math.random() * 1000));
        user.setUuid(UUID.randomUUID().toString());
        return createUser(user);
    }

    public static boolean createCiprianDummyUser(){
        User user = new User();
        user.setUsername("cipinutescu");
        user.setPassword("kip123");
        user.setFirstname("Ciprian-Ionut");
        user.setLastname("Nutescu");
        user.setPhone("0720287187");
        user.setFacebookToken(UUID.randomUUID().toString());
        user.setFacebookTokenExpiresIn((int) (Math.random() * 1000));
        user.setAuthToken(UUID.randomUUID().toString());
        user.setAuthTokenExpiresIn((int) (Math.random() * 1000));
        user.setUuid("1104425472951707");
        return createUser(user);
    }

    public static boolean createUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
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

    public static List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List result = null;
        try {
            tx = session.beginTransaction();
            result = session.createQuery("FROM User").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (List<User>) result;
    }

    public static User getUserById(Integer user_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> userList = new ArrayList<User>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List users = session.createQuery("FROM User e where e.id=" + user_id).list();
            for (Iterator iterator =
                 users.iterator(); iterator.hasNext(); ) {
                userList.add((User) iterator.next());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userList.get(0);
    }

    public static List<User> getUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> userList = new ArrayList<User>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List users = session.createQuery("FROM User e where e.username='" + username + "'").list();
            for (Iterator iterator =
                 users.iterator(); iterator.hasNext(); ) {
                userList.add((User) iterator.next());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userList;
    }

    public static boolean deleteAllUsers() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from User";
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

    public static boolean validateLogin(String username,String password){
        try {
            User user = getUserByUsername(username).get(0);
            password = new String(Hex.encodeHex(DigestUtils.sha(password)));
            return user.getPassword().equals(password);
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    public static String validateFacebookLogin(String uuid){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List users = session.createQuery("FROM User e where e.uuid='" + uuid + "'").list();
            tx.commit();
            if(users == null || users.size() == 0)
                return null;
            else
                return ((User)users.get(0)).getUsername();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
