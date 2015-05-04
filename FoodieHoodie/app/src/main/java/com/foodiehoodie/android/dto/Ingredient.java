package com.foodiehoodie.android.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jay on 4/10/15.
 */
public class Ingredient implements Serializable{
    private String name;
    private String unit;
    private ArrayList<String> nutrientsValues;

    public Ingredient(String name){
    this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ArrayList<String> getNutrientsValues() {
        return nutrientsValues;
    }

    public void setNutrientsValues(ArrayList<String> nutrientsValues) {
        this.nutrientsValues = nutrientsValues;
    }

    @Override
    public String toString() {
        return getName();
    }
}
