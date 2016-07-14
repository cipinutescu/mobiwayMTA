package com.company.bean;

import javafx.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 6/15/2016.
 */
public class GraphicBean {

    private List<Pair<Object,Object>> keysAndValues;


    public GraphicBean() {
    }

    public GraphicBean(List<Pair<Object, Object>> keysAndValues) {
        this.keysAndValues = keysAndValues;
    }

    public List<Pair<Object, Object>> getKeysAndValues() {
        return keysAndValues;
    }

    public void setKeysAndValues(List<Pair<Object, Object>> keysAndValues) {
        this.keysAndValues = keysAndValues;
    }

    @Override
    public String toString() {
        return "GraphicBean{" +
                "keysAndValues=" + keysAndValues +
                '}';
    }
}
