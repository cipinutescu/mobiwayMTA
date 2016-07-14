package com.company.machinelearning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ciprian on 6/20/2016.
 */
public class GradientDescendAlgorithm {

    public static final Double ALPHA = 0.01d;
    public static final Integer MAX_ITERATION = 100;

    public static List<Double> computeFunction(Map<Double,Double> doubleDoubleMap){
        List<Double> termens = new ArrayList<>();
        Double theta0 = 0d;
        Double theta1 = 0d;
        Double aux0;
        Double aux1;
        int current_it = 0;

        do{
            Double sum = 0d;
            for(Map.Entry<Double,Double> entry : doubleDoubleMap.entrySet()){
                sum += ((theta0 + (theta1 * entry.getKey())) - entry.getValue());
            }
            aux0 = ALPHA * sum / doubleDoubleMap.size();
            theta0 -= aux0;
            sum =0d;
            for(Map.Entry<Double,Double> entry : doubleDoubleMap.entrySet()){
                sum += (((theta0 + (theta1 * entry.getKey())) - entry.getValue()) * entry.getKey());
            }
            aux1 = ALPHA * sum / doubleDoubleMap.size();
            theta1 -= aux1;
            current_it +=1;
        }while(current_it < MAX_ITERATION);

        termens.add(theta0);
        termens.add(theta1);
        return termens;
    }

    public static List<Double> computeFuntion2(Map<Double,Double> doubleDoubleMap){
        List<Double> termens = new ArrayList<>();
        Double theta0 = 0d;
        Double theta1 = 0d;
        Double theta0c = 0d;
        Double theta1c = 0d;
        int current_it = 0;
        while (current_it < MAX_ITERATION){
            Double sum = 0d;
            for(Map.Entry<Double,Double> entry : doubleDoubleMap.entrySet()){
                sum += ((theta0 + (theta1 * entry.getKey())) - entry.getValue());
            }
            theta0c -= (ALPHA * sum / doubleDoubleMap.size());
            sum =0d;
            for(Map.Entry<Double,Double> entry : doubleDoubleMap.entrySet()){
                sum += (((theta0 + (theta1 * entry.getKey())) - entry.getValue()) * entry.getKey());
            }
            theta1c -= (ALPHA * sum / doubleDoubleMap.size());
            theta0 = theta0c;
            theta1 = theta1c;
            current_it+=1;
        }
        termens.add(theta1);
        termens.add(theta0);
        return termens;
    }
}
