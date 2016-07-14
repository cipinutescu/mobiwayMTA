package com.company.dao;

import com.company.HibernateUtil;
import com.company.model.Favorite;
import com.company.model.Location;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

/**
 * Created by Ciprian on 6/23/2016.
 */
public class FavoriteDao {
    public static final Double NORD_LAT = 44.464;
    public static final Double NORD_LONG = 22.096;

    public static final Double EST_LAT = 44.403;
    public static final Double EST_LONG = 22.166;

    public static final Double SUD_LAT = 44.386;
    public static final Double SUD_LONG = 26.124;

    public static final Double VEST_LAT = 44.429;
    public static final Double VEST_LONG = 26.671;

    public static final Double CENTER_LAT = 44.4354;
    public static final Double CENTER_LONG = 26.1022;
    public static final Long SECONDS_IN_YEAR = 31556926L;

    public static final Double RAZA = 0.12;

    private static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan nisl non nibh faucibus vestibulum. Sed dignissim, augue a consectetur mollis, neque neque placerat arcu, sed tincidunt tortor erat at enim. Nam quam dui, ornare quis nulla consequat, pharetra hendrerit erat.";

    public static boolean addNewFavorite(Favorite favorite){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(favorite);
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


    public static boolean addDummyFavorite(Integer user_id){
        Favorite location = new Favorite();
        location.setUser_id(user_id);
        Random random = new Random();
        location.setLatitude(random.nextInt(100) % 2 == 0 ?
                CENTER_LAT + Math.random() * RAZA:
                CENTER_LAT - Math.random() * RAZA);
        location.setLongitude(random.nextInt(100) % 2 == 0 ?
                CENTER_LONG + Math.random() * RAZA :
                CENTER_LONG - Math.random() * RAZA);
        location.setSpeed((int) (100 * Math.random()));
        location.setTimestamp(new Timestamp(System.currentTimeMillis()- ((long) (Math.random() * SECONDS_IN_YEAR))));
        Random random1 = new Random();
        int first = random1.nextInt(loremIpsum.length());
        int second = random1.nextInt(loremIpsum.length());
        if(first > second){
            int aux = second;
            second = first;
            first = aux;
        }
        location.setDescription(loremIpsum.substring(first,second));

        return addNewFavorite(location);
    }


    public static List<Favorite> getAllUserFavorites(Integer user_id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List locationList = null;
        try {
            tx = session.beginTransaction();
            locationList =  session.createQuery("FROM Favorite f where f.user_id=" + user_id ).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (List<Favorite>) locationList;
    }
}
