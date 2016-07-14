package com.company.controller;

import com.company.bean.GraphicBean;
import com.company.dao.JourneyDao;
import com.company.dao.JourneyDataDao;
import com.company.dao.UserContactDao;
import com.company.dao.UserDao;
import com.company.model.Journey;
import com.company.model.JourneyData;
import com.company.model.User;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Ciprian on 6/15/2016.
 */
@RestController
public class GraphicController {

    public static final Double CONSUM_MEDIU_UNIVERSAL = 5d;
    public static final Double MILISECONDS_IN_HOUR = 3600000d * 1000;

    public static final Double SEC1_LAT = 44.4585467d;
    public static final Double SEC1_LONG = 26.0629557d;

    public static final Double SEC2_LAT = 44.4567886d;
    public static final Double SEC2_LONG = 26.1252933d;

    public static final Double SEC3_LAT = 44.4194486d;
    public static final Double SEC3_LONG = 26.147386d;

    public static final Double SEC4_LAT = 44.3923377d;
    public static final Double SEC4_LONG = 26.1067968d;

    public static final Double SEC5_LAT = 44.408334d;
    public static final Double SEC5_LONG = 26.0670743d;

    public static final Double SEC6_LAT = 44.4294692d;
    public static final Double SEC6_LONG = 26.0250135d;


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getVmDCByJ", method = RequestMethod.GET)
    public
    @ResponseBody
    GraphicBean graphFunction1(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "yValue") String yValue
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            List<Pair<Object, Object>> keysAndValues = new ArrayList<Pair<Object, Object>>();
            List<Journey> journeyList = JourneyDao.getJourneyByUsername(username);
            for (Journey journey : journeyList) {
                List<JourneyData> journeyDataList = JourneyDataDao.getAllJourneyDataByJourneyName(journey.getJourneyName());
                Collections.sort(journeyDataList, new Comparator<JourneyData>() {
                    @Override
                    public int compare(JourneyData o1, JourneyData o2) {
                        return o1.getTimestamp().compareTo(o2.getTimestamp());
                    }
                });
                Double sum = 0d;
                if (yValue.equals("viteza_medie")) {
                    for (JourneyData journeyData : journeyDataList) {
                        sum += journeyData.getSpeed();
                    }
                    sum /= journeyDataList.size();
                }
                if (yValue.equals("distanta")) {
                    for (int i = 1; i < journeyDataList.size(); i++) {
                        sum += ((journeyDataList.get(i - 1).getSpeed() + journeyDataList.get(i).getSpeed()) / 2
                                * (journeyDataList.get(i).getTimestamp().getTime() - journeyDataList
                                .get(i - 1).getTimestamp().getTime()) / MILISECONDS_IN_HOUR);
                    }
                }
                if (yValue.equals("consum")) {
                    for (int i = 1; i < journeyDataList.size(); i++) {
                        sum += ((journeyDataList.get(i - 1).getSpeed() + journeyDataList.get(i).getSpeed()) / 2
                                * (journeyDataList.get(i).getTimestamp().getTime() - journeyDataList
                                .get(i - 1).getTimestamp().getTime()) / MILISECONDS_IN_HOUR);
                    }
                    sum *= (CONSUM_MEDIU_UNIVERSAL / 100d);
                }
                keysAndValues.add(new Pair<Object, Object>(journey.getJourneyName(), sum));
            }
            return new GraphicBean(keysAndValues);
        } else {
            return null;
        }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getVmDCByUF", method = RequestMethod.GET)
    public
    @ResponseBody
    GraphicBean graphFunction2(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "yValue") String yValue
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            List<User> userList = UserContactDao.getAllFriendsByUsername(username);
            userList.add(UserDao.getUserByUsername(username).get(0));
            List<Pair<Object, Object>> keysAndValues = new ArrayList<Pair<Object, Object>>();
            for (User user : userList) {
                List<Journey> journeyList = JourneyDao.getJourneyByUsername(user.getUsername());
                Double sumUser = 0d;
                for (Journey journey : journeyList) {
                    List<JourneyData> journeyDataList = JourneyDataDao.getAllJourneyDataByJourneyName(journey.getJourneyName());
                    Collections.sort(journeyDataList, new Comparator<JourneyData>() {
                        @Override
                        public int compare(JourneyData o1, JourneyData o2) {
                            return o1.getTimestamp().compareTo(o2.getTimestamp());
                        }
                    });
                    Double sum = 0d;
                    if (yValue.equals("viteza_medie")) {
                        for (JourneyData journeyData : journeyDataList) {
                            sum += journeyData.getSpeed();
                        }
                        sum /= journeyDataList.size();
                    }
                    if (yValue.equals("distanta")) {
                        for (int i = 1; i < journeyDataList.size(); i++) {
                            sum += ((journeyDataList.get(i - 1).getSpeed() + journeyDataList.get(i).getSpeed()) / 2
                                    * (journeyDataList.get(i).getTimestamp().getTime() - journeyDataList
                                    .get(i - 1).getTimestamp().getTime()) / MILISECONDS_IN_HOUR);
                        }
                    }
                    if (yValue.equals("consum")) {
                        for (int i = 1; i < journeyDataList.size(); i++) {
                            sum += ((journeyDataList.get(i - 1).getSpeed() + journeyDataList.get(i).getSpeed()) / 2
                                    * (journeyDataList.get(i).getTimestamp().getTime() - journeyDataList
                                    .get(i - 1).getTimestamp().getTime()) / MILISECONDS_IN_HOUR);
                        }
                        sum *= (CONSUM_MEDIU_UNIVERSAL / 100d);
                    }
                    sumUser += sum;
                }
                keysAndValues.add(new Pair<Object, Object>(user.getUsername(), sumUser / journeyList.size()));
            }
            return new GraphicBean(keysAndValues);
        } else {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getPartBySD", method = RequestMethod.GET)
    public
    @ResponseBody
    GraphicBean graphFunction3(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "xValue") String xValue,
            @RequestParam(value = "yValue") String yValue
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            List<Pair<Object, Object>> keysAndValues = new ArrayList<Pair<Object, Object>>();
            List<JourneyData> journeyDataList = null;
            if (xValue.equals("me"))
                journeyDataList = JourneyDataDao.getAllJourneyData(username);
            else
                journeyDataList = JourneyDataDao.getFullJourneyDataForAllUsers();
            if (yValue.equals("sectoare")) {
                int sector1 = 0;
                int sector2 = 0;
                int sector3 = 0;
                int sector4 = 0;
                int sector5 = 0;
                int sector6 = 0;
                for (JourneyData journeyData : journeyDataList) {
                    switch (deteminateSector(journeyData)) {
                        case "sector1":
                            sector1 += 1;
                            break;
                        case "sector2":
                            sector2 += 1;
                            break;
                        case "sector3":
                            sector3 += 1;
                            break;
                        case "sector4":
                            sector4 += 1;
                            break;
                        case "sector5":
                            sector5 += 1;
                            break;
                        case "sector6":
                            sector6 += 1;
                            break;
                        default:
                            break;
                    }
                }
                keysAndValues.add(new Pair<Object, Object>("sector1", sector1));
                keysAndValues.add(new Pair<Object, Object>("sector2", sector2));
                keysAndValues.add(new Pair<Object, Object>("sector3", sector3));
                keysAndValues.add(new Pair<Object, Object>("sector4", sector4));
                keysAndValues.add(new Pair<Object, Object>("sector5", sector5));
                keysAndValues.add(new Pair<Object, Object>("sector6", sector6));
            }
            if (yValue.equals("perioade")) {
                int dim = 0;
                int pranz = 0;
                int seara = 0;
                int noapte = 0;
                for (JourneyData journeyData : journeyDataList) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(journeyData.getTimestamp());
                    if (6 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) < 10)
                        dim += 1;
                    else if (10 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) < 18)
                        pranz += 1;
                    else if (18 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) < 21)
                        seara += 1;
                    else
                        noapte += 1;
                }
                keysAndValues.add(new Pair<Object, Object>("dim", dim));
                keysAndValues.add(new Pair<Object, Object>("pranz", pranz));
                keysAndValues.add(new Pair<Object, Object>("seara", seara));
                keysAndValues.add(new Pair<Object, Object>("noapte", noapte));
            }
            return new GraphicBean(keysAndValues);
        } else {
            return null;
        }
    }

    private String deteminateSector(JourneyData point) {
        Pair<String, Double> sectorDist1 = new Pair<String, Double>("sector1", euclidianDistance(point.getLatitude(), point.getLongitude(), SEC1_LAT, SEC1_LONG));
        Pair<String, Double> sectorDist2 = new Pair<String, Double>("sector2", euclidianDistance(point.getLatitude(), point.getLongitude(), SEC2_LAT, SEC2_LONG));
        Pair<String, Double> sectorDist3 = new Pair<String, Double>("sector3", euclidianDistance(point.getLatitude(), point.getLongitude(), SEC3_LAT, SEC3_LONG));
        Pair<String, Double> sectorDist4 = new Pair<String, Double>("sector4", euclidianDistance(point.getLatitude(), point.getLongitude(), SEC4_LAT, SEC4_LONG));
        Pair<String, Double> sectorDist5 = new Pair<String, Double>("sector5", euclidianDistance(point.getLatitude(), point.getLongitude(), SEC5_LAT, SEC5_LONG));
        Pair<String, Double> sectorDist6 = new Pair<String, Double>("sector6", euclidianDistance(point.getLatitude(), point.getLongitude(), SEC6_LAT, SEC6_LONG));
        List<Pair<String, Double>> list = new ArrayList<Pair<String, Double>>();
        list.add(sectorDist1);
        list.add(sectorDist2);
        list.add(sectorDist3);
        list.add(sectorDist4);
        list.add(sectorDist5);
        list.add(sectorDist6);
        Pair<String, Double> doublePair = new Pair<String, Double>("", Double.MAX_VALUE);
        for (Pair<String, Double> pair : list) {
            if (pair.getValue() < doublePair.getValue())
                doublePair = pair;
        }
        return doublePair.getKey();
    }

    private Double euclidianDistance(Double lat1, Double long1, Double lat2, Double long2) {
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(long1 - long2, 2));
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getTechG", method = RequestMethod.GET)
    public
    @ResponseBody
    GraphicBean graphFunction4(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "xValue") String xValue,
            @RequestParam(value = "yValue") String yValue
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            if (xValue.equals("me")) {
                List<Pair<Object, Object>> keysAndValues = new ArrayList<Pair<Object, Object>>();
                if (yValue.equals("journey_data")) {
                    List<Journey> journeyList = JourneyDao.getJourneyByUsername(username);
                    for (Journey journey : journeyList) {
                        int nbP = JourneyDataDao.getAllJourneyDataByJourneyName(journey.getJourneyName()).size();
                        keysAndValues.add(new Pair<Object, Object>(journey.getJourneyName(), nbP));
                    }
                }
                if (yValue.equals("time")) {
                    List<Journey> journeyList = JourneyDao.getJourneyByUsername(username);
                    for (Journey journey : journeyList) {
                        List<JourneyData> journeyDataList = JourneyDataDao.getAllJourneyDataByJourneyName(journey.getJourneyName());
                        Collections.sort(journeyDataList, new Comparator<JourneyData>() {
                            @Override
                            public int compare(JourneyData o1, JourneyData o2) {
                                return o1.getTimestamp().compareTo(o2.getTimestamp());
                            }
                        });
                        long tpjD = journeyDataList.get(0).getTimestamp().getTime() - journeyDataList.get(journeyDataList.size()-1).getTimestamp().getTime();
                        keysAndValues.add(new Pair<Object, Object>(journey.getJourneyName(),-tpjD / MILISECONDS_IN_HOUR));
                    }
                }
                return new GraphicBean(keysAndValues);
            }
            if (xValue.equals("friends")) {
                List<Pair<Object, Object>> keysAndValues = new ArrayList<Pair<Object, Object>>();
                List<User> userList = UserContactDao.getAllFriendsByUsername(username);
                userList.add(UserDao.getUserByUsername(username).get(0));
                if (yValue.equals("journey")) {
                    for(User user : userList){
                        int n = JourneyDao.getJourneyByUsername(user.getUsername()).size();
                        keysAndValues.add(new Pair<Object, Object>(user.getUsername(),n));
                    }
                }
                if (yValue.equals("journey_data")) {
                    for(User user : userList){
                        int n = JourneyDataDao.getAllJourneyData(user.getUsername()).size();
                        keysAndValues.add(new Pair<Object, Object>(user.getUsername(),n));
                    }
                }
                if (yValue.equals("time")) {
                    for(User user : userList) {
                        List<JourneyData> journeyDataList = JourneyDataDao.getAllJourneyData(user.getUsername());
                        if(journeyDataList.size() == 0){
                            keysAndValues.add(new Pair<Object, Object>(user.getUsername(), 0d));
                            continue;
                        }
                        Collections.sort(journeyDataList, new Comparator<JourneyData>() {
                            @Override
                            public int compare(JourneyData o1, JourneyData o2) {
                                return o1.getTimestamp().compareTo(o2.getTimestamp());
                            }
                        });
                        long tpjD = journeyDataList.get(0).getTimestamp().getTime() - journeyDataList.get(journeyDataList.size()-1).getTimestamp().getTime();
                        keysAndValues.add(new Pair<Object, Object>(user.getUsername(),- tpjD / MILISECONDS_IN_HOUR));
                    }
                }
                return new GraphicBean(keysAndValues);
            }
        } else {
            return null;
        }
        return null;
    }



    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getGraphTime", method = RequestMethod.GET)
    public
    @ResponseBody
    GraphicBean graphFunction5(@RequestParam(value = "username") String username,
                               @RequestParam(value = "token") String token
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            List<JourneyData> allJourneyDataList = JourneyDataDao.getFullJourneyDataForAllUsers();
            List<Integer> hours = new ArrayList<>();
            List<Integer> app = new ArrayList<>();
            for(int i=0;i<24;i++){
                hours.add(i);
                app.add(0);
            }

            for(JourneyData journeyData : allJourneyDataList){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(journeyData.getTimestamp());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                app.set(hour,app.get(hour)+1);
            }
            List<Pair<Object, Object>> keysAndValues = new ArrayList<>();
            for(int i=0;i< hours.size();i++){
                keysAndValues.add(new Pair<Object, Object>(hours.get(i),app.get(i)));
            }
            return new GraphicBean(keysAndValues);
        } else {
            return null;
        }
    }
}
