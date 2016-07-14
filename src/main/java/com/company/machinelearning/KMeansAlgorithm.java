package com.company.machinelearning;

import com.company.dao.JourneyDataDao;
import com.company.enums.StrategyEnums;
import com.company.model.JourneyData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Ciprian on 6/13/2016.
 */
public class KMeansAlgorithm {

    public static final Integer NUMBER_OF_ITERATIONS = 300;

    public static List<JourneyData> computeTraficAmbutejayjeCentroidsByTime(int hour) {
        List<JourneyData> journeyDataListx = JourneyDataDao.getFullJourneyDataForAllUsers();
        List<JourneyData> journeyDataList = new ArrayList<>();
        for (JourneyData journeyData : journeyDataListx) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(journeyData.getTimestamp());
            if (calendar.get(Calendar.HOUR_OF_DAY) > hour - 1 || calendar.get(Calendar.HOUR_OF_DAY) < hour + 1)
                journeyDataList.add(journeyData);
        }
        if(journeyDataList.size() < 20 )
            return null;

        int numberOfPoints = journeyDataList.size() / 20;
        List<JourneyData> centronoms = new ArrayList<JourneyData>();
        List<Integer> pointsPairedToCentronoms = new ArrayList<Integer>();
        int numberOfRandom = 0;
        Random randomIntGenerator = new Random();
        while (numberOfRandom < numberOfPoints) {
            int randomIndex = randomIntGenerator.nextInt(journeyDataList.size() - 1);
            if (!centronoms.contains(journeyDataList.get(randomIndex))) {
                centronoms.add(journeyDataList.get(randomIndex));
                numberOfRandom += 1;
            }
        }
        Integer currentIteration = 0;
        while (currentIteration < NUMBER_OF_ITERATIONS) {
            for (JourneyData jd : journeyDataList) {
                pointsPairedToCentronoms.add(centronoms.indexOf(getClosestCentronom(jd, centronoms, StrategyEnums.THREEDIM)));
            }
            List<JourneyData> newCentronoms = new ArrayList<JourneyData>();
            for (int i = 0; i < numberOfPoints; i++) {
                double medianLatitude = 0d;
                double medianLongitude = 0d;
                double medianTimeStamp = 0d;
                int latNb = 0;
                for (int j = 0; j < pointsPairedToCentronoms.size(); j++) {
                    if (pointsPairedToCentronoms.get(j) == i) {
                        medianLatitude += journeyDataList.get(j).getLatitude();
                        medianLongitude += journeyDataList.get(j).getLongitude();
                        medianTimeStamp += journeyDataList.get(j).getTimestamp().getTime() * 1.d;
                        latNb += 1;
                    }
                }
                JourneyData jdNewCentronom = new JourneyData();
                jdNewCentronom.setLongitude(medianLongitude / latNb);
                jdNewCentronom.setLatitude(medianLatitude / latNb);
                jdNewCentronom.setTimestamp(new Timestamp((long) (medianTimeStamp / latNb)));
                newCentronoms.add(jdNewCentronom);
            }
            centronoms.clear();
            pointsPairedToCentronoms.clear();
            centronoms.addAll(newCentronoms);
            newCentronoms.clear();
            currentIteration += 1;
        }

        return centronoms;
    }

    public static List<JourneyData> computeTraficAmbutejayjeCentroids(StrategyEnums strategyEnums) {
        List<JourneyData> journeyDataList = JourneyDataDao.getFullJourneyDataForAllUsers();
        int numberOfPoints = journeyDataList.size() / 20;
        List<JourneyData> centronoms = new ArrayList<JourneyData>();
        List<Integer> pointsPairedToCentronoms = new ArrayList<Integer>();
        int numberOfRandom = 0;
        Random randomIntGenerator = new Random();
        while (numberOfRandom < numberOfPoints) {
            int randomIndex = randomIntGenerator.nextInt(journeyDataList.size() - 1);
            if (!centronoms.contains(journeyDataList.get(randomIndex))) {
                centronoms.add(journeyDataList.get(randomIndex));
                numberOfRandom += 1;
            }
        }
        Integer currentIteration = 0;
        while (currentIteration < NUMBER_OF_ITERATIONS) {
            for (JourneyData jd : journeyDataList) {
                pointsPairedToCentronoms.add(centronoms.indexOf(getClosestCentronom(jd, centronoms, strategyEnums)));
            }
            List<JourneyData> newCentronoms = new ArrayList<JourneyData>();
            for (int i = 0; i < numberOfPoints; i++) {
                double medianLatitude = 0d;
                double medianLongitude = 0d;
                double medianTimeStamp = 0d;
                int latNb = 0;
                for (int j = 0; j < pointsPairedToCentronoms.size(); j++) {
                    if (pointsPairedToCentronoms.get(j) == i) {
                        medianLatitude += journeyDataList.get(j).getLatitude();
                        medianLongitude += journeyDataList.get(j).getLongitude();
                        medianTimeStamp += journeyDataList.get(j).getTimestamp().getTime() * 1.d;
                        latNb += 1;
                    }
                }
                JourneyData jdNewCentronom = new JourneyData();
                jdNewCentronom.setLongitude(medianLongitude / latNb);
                jdNewCentronom.setLatitude(medianLatitude / latNb);
                jdNewCentronom.setTimestamp(new Timestamp((long) (medianTimeStamp / latNb)));
                if(latNb != 0)
                    newCentronoms.add(jdNewCentronom);
            }
            centronoms.clear();
            pointsPairedToCentronoms.clear();
            centronoms.addAll(newCentronoms);
            currentIteration += 1;
        }

        return centronoms;
    }

    private static JourneyData getClosestCentronom(JourneyData point, List<JourneyData> centronoms, StrategyEnums strategyEnums) {
        JourneyData clossestCentronom = null;
        double minDistance = Double.MAX_VALUE;
        for (JourneyData centronom : centronoms) {
            double euclidianDistance = euclidianDistance(centronom, point, strategyEnums);
            if (euclidianDistance < minDistance) {
                clossestCentronom = centronom;
                minDistance = euclidianDistance;
            }
        }
        return clossestCentronom;
    }

    private static Double euclidianDistance(JourneyData point1, JourneyData point2, StrategyEnums strategyEnums) {
        Double sum = 0d;
        if (StrategyEnums.TWODIM.equals(strategyEnums)) {
            return harversianeDistance(point1, point2);
        } else {
            sum += (Math.pow((point1.getLatitude() - point2.getLatitude()) * 110.574d, 2) + Math.pow((point1.getLongitude()
                    - point2.getLongitude()) * 111.320d * Math.cos((point1.getLatitude() - point2.getLatitude()) * Math.PI / 180), 2) + Math.pow(point1.getTimestamp().getTime() - point2.getTimestamp().getTime()
                    , 2));
        }
        return Math.sqrt(sum);
    }

    public static Double harversianeDistance(JourneyData point1, JourneyData point2) {
        Double deltaLong = degreeToRadians(point1.getLongitude() - point2.getLongitude());
        Double deltaLat = degreeToRadians(point1.getLatitude() - point2.getLatitude());
        Double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(degreeToRadians(point1.getLatitude())) * Math.cos(point2.getLatitude()) +
                Math.sin(deltaLong / 2) * Math.sin(deltaLong / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6.371 * c;
    }

    private static Double degreeToRadians(Double degree) {
        return Math.abs(degree) * Math.PI / 180;
    }
}
