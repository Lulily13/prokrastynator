package com.tigers;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Report3 implements Segregator {

    private final String selectedEmployee;
    private final String selectedYear;

    public Report3 (String selectedEmployee, String selectedYear){
        this.selectedEmployee = selectedEmployee;
        this.selectedYear = selectedYear;
    }

    @Override
    public Collection<String> prepareReport3(DataCollector dataCollector){
        //Struktura: Projekt --> Godziny
        Map<String,Double> projectHours=new TreeMap<>();
    }
}
