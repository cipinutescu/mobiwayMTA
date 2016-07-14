package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Main {
    public static final Double CENTER_LAT = 44.432;
    public static final Double CENTER_LONG = 26.098;
    public static final Long SECONDS_IN_YEAR = 31556926L;

    public static final Double RAZA = 0.5;

    public static void main(String[] args) {
        //UserDao.deleteAllUsers();
        //UserDao.createDummyUser("Ciprian");
        //JourneyDao.deleteAllJourney();
        //JourneyDao.addDummyJourney(21);
        /*System.out.println(JourneyDao.getJourneyByUsername("Ciprian"));
        JourneyDataDao.deleteAllJourneyData();
        JourneyDataDao.generateDummyJourneyData(6);
        JourneyDataDao.generateDummyJourneyData(6);
        JourneyDataDao.generateDummyJourneyData(6);
        JourneyDataDao.generateDummyJourneyData(6);
        System.out.println(JourneyDataDao.getAllJourneyData("Ciprian"));
        UserContact userContact = new UserContact();
        userContact.setId_user(21);
        userContact.setId_friend_user(22);
        UserContactDao.createUserContact(userContact);
        System.out.println(UserContactDao.getAllFriendsByUsername("Ciprian"));*/
        //LocationDao.addNewDummyLocation(21);
        //System.out.println(LocationDao.getLastUserLocation(21));
        //System.out.println(JourneyDataDao.getLastKnowPosition("Ciprian"));
        //UserDao.createCiprianDummyUser();
        //LocationDao.addNewDummyLocation(22);
        //LocationDao.addNewDummyLocation(24);
        /*
        for(int k=0;k<5;k++){
            JourneyDao.addDummyJourney(24);
            for(int j=0;j<1000;j++){
                JourneyDataDao.generateDummyJourneyData(k+8);
            }
            List<Journey> journeyList = JourneyDao.getJourneyByUsername("cipinutescu");
            Map<Double, Double> doubleDoubleMap = new HashMap<>();
            for (Journey journey : journeyList) {
                List<JourneyData> journeyDataList = JourneyDataDao.getAllJourneyDataByJourneyName(journey.getJourneyName());
                Collections.sort(journeyDataList, new Comparator<JourneyData>() {
                    @Override
                    public int compare(JourneyData o1, JourneyData o2) {
                        return o1.getTimestamp().compareTo(o2.getTimestamp());
                    }
                });
                for (int i = 0; i < journeyDataList.size() - 1; i++) {
                    Double dist = KMeansAlgorithm.harversianeDistance(journeyDataList.get(i), journeyDataList.get(i + 1));
                    doubleDoubleMap.put((journeyDataList.get(i).getSpeed() - journeyDataList.get(i + 1).getSpeed()) / 2d,
                            dist * GraphicController.CONSUM_MEDIU_UNIVERSAL / 100);
                }
            }
            System.out.println(GradientDescendAlgorithm.computeFuntion(doubleDoubleMap));
            System.out.println(KMeansAlgorithm.computeTraficAmbutejayjeCentroids(StrategyEnums.TWODIM));
        }
        */

        SpringApplication.run(Main.class, args);


        /*Mocker.createMockedCoreUsers();
        Mocker.createMockedDummyUsers(8);
        Mocker.createJourneyAndJourneyDataCore();
        Mocker.createJourneyAndJourneyDataDummy();
        Mocker.mockCoreLocations();
        Mocker.mockDummyLocation();*/
    }
}
