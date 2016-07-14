package com.company.controller;

import com.company.bean.GraphicBean;
import com.company.dao.JourneyDao;
import com.company.dao.JourneyDataDao;
import com.company.enums.StrategyEnums;
import com.company.machinelearning.GradientDescendAlgorithm;
import com.company.machinelearning.KMeansAlgorithm;
import com.company.model.Journey;
import com.company.model.JourneyData;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Ciprian on 6/13/2016.
 */
@RestController
public class MachineLearningController {

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getCargoCenters2D", method = RequestMethod.GET)
    public
    @ResponseBody
    List<JourneyData> getAglomerationalCenters(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            return KMeansAlgorithm.computeTraficAmbutejayjeCentroids(StrategyEnums.THREEDIM);
        } else {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getCargoCenters3D", method = RequestMethod.GET)
    public
    @ResponseBody
    List<JourneyData> getAglomerationalCenters2(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            return KMeansAlgorithm.computeTraficAmbutejayjeCentroids(StrategyEnums.THREEDIM);
        } else {
            return null;
        }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getCargoCentersByTime", method = RequestMethod.GET)
    public
    @ResponseBody
    List<JourneyData> getAglomerationalCenters2(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "hour") Integer hour
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            return KMeansAlgorithm.computeTraficAmbutejayjeCentroidsByTime(hour);
        } else {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getConsumeStatistics", method = RequestMethod.GET)
    public
    @ResponseBody
    GraphicBean getConsumeStatistics(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "token") String token
    ) {
        if (AuthentificationController.authentificationTokens.containsKey(username)
                && AuthentificationController.authentificationTokens.containsValue(token)) {
            List<Journey> journeyList = JourneyDao.getJourneyByUsername(username);
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
                    doubleDoubleMap.put(Math.abs((journeyDataList.get(i).getSpeed() - journeyDataList.get(i + 1).getSpeed()) / 2d),
                            Math.abs(dist * GraphicController.CONSUM_MEDIU_UNIVERSAL / 100));
                }
            }
            List<Double> terms = GradientDescendAlgorithm.computeFunction(doubleDoubleMap);
            List<Pair<Object, Object>> keysAndValues = new ArrayList<>();
            List<JourneyData> journeyDataList = JourneyDataDao.getAllJourneyData(username);
            for (JourneyData journeyData : journeyDataList) {
                keysAndValues.add(new Pair<Object, Object>(new Double(journeyData.getSpeed()), terms.get(0) + terms.get(1) * journeyData.getSpeed()));
            }
            Collections.sort(keysAndValues, new Comparator<Pair<Object, Object>>() {
                @Override
                public int compare(Pair<Object, Object> o1, Pair<Object, Object> o2) {
                    return ((Double)o1.getKey()).compareTo((Double)o2.getKey());
                }
            });
            return new GraphicBean(keysAndValues);
        } else {
            return null;
        }
    }

}
