package com.company.mock;

import com.company.dao.*;
import com.company.model.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Ciprian on 7/3/2016.
 */
public class Mocker {

    public static final Double CENTER_LAT = 44.4354;
    public static final Double CENTER_LONG = 26.1022;
    public static final Long SECONDS_IN_YEAR = 31556926L;

    public static final Double RAZA = 0.12;


    public static void createMockedCoreUsers() {

        // main demo user
        User user = new User();
        user.setUuid("1104425472951707");
        user.setUsername("ciprian93");
        user.setPassword("pass123");
        user.setFirstname("Ciprian-Ionut");
        user.setLastname("Nutescu");
        user.setPhone("0720287187");
        user.setAuthTokenExpiresIn(0);
        user.setFacebookTokenExpiresIn(0);
        user.setFacebookToken(UUID.randomUUID().toString());
        user.setAuthToken(UUID.randomUUID().toString());
        UserDao.createUser(user);

        // main demo user friends
        User friend1 = new User();
        friend1.setUuid(UUID.randomUUID().toString());
        friend1.setUsername("dragos93");
        friend1.setPassword("pass123");
        friend1.setFirstname("Dragos Daniel");
        friend1.setLastname("Visan");
        friend1.setPhone("0720321905");
        friend1.setAuthTokenExpiresIn(0);
        friend1.setFacebookTokenExpiresIn(0);
        friend1.setFacebookToken(UUID.randomUUID().toString());
        friend1.setAuthToken(UUID.randomUUID().toString());

        User friend2 = new User();
        friend2.setUuid(UUID.randomUUID().toString());
        friend2.setUsername("adrian93");
        friend2.setPassword("pass123");
        friend2.setFirstname("Adrian");
        friend2.setLastname("Dobrica");
        friend2.setPhone("072891920");
        friend2.setAuthTokenExpiresIn(0);
        friend2.setFacebookTokenExpiresIn(0);
        friend2.setFacebookToken(UUID.randomUUID().toString());
        friend2.setAuthToken(UUID.randomUUID().toString());

        UserDao.createUser(friend1);
        UserDao.createUser(friend2);

        User friend10 = UserDao.getUserByUsername("ciprian93").get(0);
        User friend11 = UserDao.getUserByUsername("adrian93").get(0);
        User friend12 = UserDao.getUserByUsername("dragos93").get(0);

        //create friendship relations
        UserContact userContact1 = new UserContact();
        userContact1.setId_user(friend10.getId());
        userContact1.setId_friend_user(friend11.getId());
        UserContactDao.createUserContact(userContact1);

        UserContact userContact2 = new UserContact();
        userContact2.setId_user(friend11.getId());
        userContact2.setId_friend_user(friend10.getId());
        UserContactDao.createUserContact(userContact2);

        UserContact userContact3 = new UserContact();
        userContact3.setId_user(friend10.getId());
        userContact3.setId_friend_user(friend12.getId());
        UserContactDao.createUserContact(userContact3);

        UserContact userContact4 = new UserContact();
        userContact4.setId_user(friend12.getId());
        userContact4.setId_friend_user(friend10.getId());
        UserContactDao.createUserContact(userContact4);
    }

    public static void createMockedDummyUsers(int numberOfUsers) {
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setUuid(UUID.randomUUID().toString());
            user.setUsername("user" + i);
            user.setPassword("pass123");
            user.setFirstname("FirstName");
            user.setLastname("LastName");
            user.setPhone("072000000");
            user.setAuthTokenExpiresIn(0);
            user.setFacebookTokenExpiresIn(0);
            user.setFacebookToken(UUID.randomUUID().toString());
            user.setAuthToken(UUID.randomUUID().toString());
            UserDao.createUser(user);
        }
    }

    public static void createJourneyAndJourneyDataCore() {
        //get mainUsers
        User friend10 = UserDao.getUserByUsername("ciprian93").get(0);
        User friend11 = UserDao.getUserByUsername("adrian93").get(0);
        User friend12 = UserDao.getUserByUsername("dragos93").get(0);

        //create journeys
        for (int i = 0; i < 5; i++) {
            Journey journey = new Journey();
            journey.setJourneyName("jouney0" + i);
            journey.setIdUser(friend10.getId());
            JourneyDao.addNewJourney(journey);
        }

        for (int i = 0; i < 3; i++) {
            Journey journey = new Journey();
            journey.setJourneyName("jouney1" + i);
            journey.setIdUser(friend11.getId());
            JourneyDao.addNewJourney(journey);
        }

        for (int i = 0; i < 3; i++) {
            Journey journey = new Journey();
            journey.setJourneyName("jouney2" + i);
            journey.setIdUser(friend12.getId());
            JourneyDao.addNewJourney(journey);
        }

        //get saved Journeys
        List<Journey> journeyList00 = JourneyDao.getJourneyByUsername(friend10.getUsername());
        List<Journey> journeyList01 = JourneyDao.getJourneyByUsername(friend11.getUsername());
        List<Journey> journeyList02 = JourneyDao.getJourneyByUsername(friend12.getUsername());

        //add journey data
        for (Journey journey : journeyList00) {
            Random random = new Random();
            int maxElems = random.nextInt(30)+1;
            for (int i = 0; i < maxElems; i++) {
                JourneyData journeyData = new JourneyData();
                journeyData.setJourney_id(journey.getId());
                journeyData.setLatitude(random.nextInt() % 2 == 0 ? CENTER_LAT + Math.random() * RAZA :
                        CENTER_LAT - Math.random() * RAZA);
                journeyData.setLongitude(random.nextInt() % 2 == 0 ? CENTER_LONG + Math.random() * RAZA:
                        CENTER_LONG - Math.random() * RAZA);
                journeyData.setSpeed((int) (150 * Math.random()));
                journeyData.setTimestamp(new Timestamp(System.currentTimeMillis() - ((long) (Math.random() * SECONDS_IN_YEAR * 1000))));
                journeyData.setOsm_way_id(UUID.randomUUID().toString());
                JourneyDataDao.addNewJourneyData(journeyData);
            }
        }

        for (Journey journey : journeyList01) {
            Random random = new Random();
            int maxElems = random.nextInt(15)+1;
            for (int i = 0; i < maxElems; i++) {
                JourneyData journeyData = new JourneyData();
                journeyData.setJourney_id(journey.getId());
                journeyData.setLatitude(random.nextInt() % 2 == 0 ? CENTER_LAT + Math.random() * RAZA :
                        CENTER_LAT - Math.random() * RAZA);
                journeyData.setLongitude(random.nextInt() % 2 == 0 ? CENTER_LONG + Math.random() * RAZA:
                        CENTER_LONG - Math.random() * RAZA);
                journeyData.setSpeed((int) (150 * Math.random()));
                journeyData.setTimestamp(new Timestamp(System.currentTimeMillis() - ((long) (Math.random() * SECONDS_IN_YEAR * 1000))));
                journeyData.setOsm_way_id(UUID.randomUUID().toString());
                JourneyDataDao.addNewJourneyData(journeyData);
            }
        }

        for (Journey journey : journeyList02) {
            Random random = new Random();
            int maxElems = random.nextInt(15)+1;
            for (int i = 0; i < maxElems; i++) {
                JourneyData journeyData = new JourneyData();
                journeyData.setJourney_id(journey.getId());
                journeyData.setLatitude(random.nextInt() % 2 == 0 ? CENTER_LAT + Math.random() * RAZA :
                        CENTER_LAT - Math.random() * RAZA);
                journeyData.setLongitude(random.nextInt() % 2 == 0 ? CENTER_LONG + Math.random() * RAZA:
                        CENTER_LONG - Math.random() * RAZA);
                journeyData.setSpeed((int) (150 * Math.random()));
                journeyData.setTimestamp(new Timestamp(System.currentTimeMillis() - ((long) (Math.random() * SECONDS_IN_YEAR * 1000))));
                journeyData.setOsm_way_id(UUID.randomUUID().toString());
                JourneyDataDao.addNewJourneyData(journeyData);
            }
        }
    }

    public static void createJourneyAndJourneyDataDummy() {

        for (int i = 0; i < 8; i++) {
            User user = UserDao.getUserByUsername("user" + i).get(0);
            Random randomJ = new Random();
            int maxJElems = randomJ.nextInt(3);
            for (int j = 0; j < maxJElems; j++) {
                Journey journey = new Journey();
                journey.setIdUser(user.getId());
                journey.setJourneyName(UUID.randomUUID().toString());
                JourneyDao.addNewJourney(journey);
            }
            List<Journey> journeyList = JourneyDao.getJourneyByUsername("user" + i);
            for (Journey journey : journeyList) {
                Random random = new Random();
                int maxElems = random.nextInt(10);
                for (int j = 0; j < maxElems; j++) {
                    JourneyData journeyData = new JourneyData();
                    journeyData.setJourney_id(journey.getId());
                    journeyData.setLatitude(random.nextInt() % 2 == 0 ? CENTER_LAT + Math.random() * RAZA :
                            CENTER_LAT - Math.random() * RAZA);
                    journeyData.setLongitude(random.nextInt() % 2 == 0 ? CENTER_LONG + Math.random() * RAZA:
                            CENTER_LONG - Math.random() * RAZA);
                    journeyData.setSpeed((int) (150 * Math.random()));
                    journeyData.setTimestamp(new Timestamp(System.currentTimeMillis() - ((long) (Math.random() * SECONDS_IN_YEAR * 1000))));
                    journeyData.setOsm_way_id(UUID.randomUUID().toString());
                    JourneyDataDao.addNewJourneyData(journeyData);
                }
            }
        }
    }

    public static void mockCoreLocations() {
        Random random = new Random();

        User user13 = UserDao.getUserByUsername("ciprian93").get(0);
        Location location2 = new Location();
        location2.setLatitude(random.nextInt() % 2 == 0 ? CENTER_LAT + Math.random() * RAZA:
                CENTER_LAT - Math.random() * RAZA);
        location2.setLongitude(random.nextInt() % 2 == 0 ? CENTER_LONG + Math.random() * RAZA:
                CENTER_LONG - Math.random() * RAZA);
        location2.setSpeed((int) (150 * Math.random()));
        location2.setTimestamp(new Timestamp(System.currentTimeMillis() - ((long) (Math.random() * SECONDS_IN_YEAR * 1000))));
        location2.setUser_id(user13.getId());
        LocationDao.addNewLocation(location2);


        User user11 = UserDao.getUserByUsername("dragos93").get(0);
        Location location = new Location();
        location.setLatitude(random.nextInt() % 2 == 0 ? CENTER_LAT + Math.random() * RAZA:
                CENTER_LAT - Math.random() * RAZA);
        location.setLongitude(random.nextInt() % 2 == 0 ? CENTER_LONG + Math.random() * RAZA:
                CENTER_LONG - Math.random() * RAZA);
        location.setSpeed((int) (150 * Math.random()));
        location.setTimestamp(new Timestamp(System.currentTimeMillis() - ((long) (Math.random() * SECONDS_IN_YEAR * 1000))));
        location.setUser_id(user11.getId());
        LocationDao.addNewLocation(location);

        User user12 = UserDao.getUserByUsername("adrian93").get(0);
        Location location1 = new Location();
        location1.setLatitude(random.nextInt() % 2 == 0 ? CENTER_LAT + Math.random() * RAZA:
                CENTER_LAT - Math.random() * RAZA);
        location1.setLongitude(random.nextInt() % 2 == 0 ? CENTER_LONG + Math.random() * RAZA:
                CENTER_LONG - Math.random() * RAZA);
        location1.setSpeed((int) (150 * Math.random()));
        location1.setTimestamp(new Timestamp(System.currentTimeMillis() - ((long) (Math.random() * SECONDS_IN_YEAR * 1000))));
        location1.setUser_id(user12.getId());
        LocationDao.addNewLocation(location1);
    }

    public static void mockDummyLocation() {
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            User user12 = UserDao.getUserByUsername("user" + i).get(0);
            Location location1 = new Location();
            location1.setLatitude(random.nextInt() % 2 == 0 ? CENTER_LAT + Math.random() * RAZA:
                    CENTER_LAT - Math.random() * RAZA);
            location1.setLongitude(random.nextInt() % 2 == 0 ? CENTER_LONG + Math.random() * RAZA:
                    CENTER_LONG - Math.random() * RAZA);
            location1.setSpeed((int) (150 * Math.random()));
            location1.setTimestamp(new Timestamp(System.currentTimeMillis() - ((long) (Math.random() * SECONDS_IN_YEAR * 1000))));
            location1.setUser_id(user12.getId());
            LocationDao.addNewLocation(location1);
        }
    }

}
