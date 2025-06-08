package com.tigers;

import java.util.*;

public class Report3 implements Segregator {

    private String employee;

    public Report3(String employee) {
        this.employee = employee;
    }


    public List<String> prepareReport(DataCollector dataCollector){
        Map<String, Double> reportMap = new TreeMap<>();


        for (Task task : dataCollector.getTasks()) {
            if (task.getEmployee().equals(employee)) {
                String year = task.getYear();
                String month = task.getMonth();
                String project = task.getProjectName();

                String dateKey = String.format("%s-%02d", year, Integer.parseInt(month));
                String fullKey = dateKey + "|" + project;

                double hours = task.getHours();
                reportMap.put(fullKey, reportMap.getOrDefault(fullKey, 0.0) + hours);
            }
        }

        List<String> report = new ArrayList<>();
        int counter = 1;
        for (Map.Entry<String, Double> entry : reportMap.entrySet()) {
            String[] parts = entry.getKey().split("\\|");
            String date = parts[0];
            String project = parts[1];
            double hours = entry.getValue();

            String line = String.format("%d. %s - %s - %.2f godzin", counter++, date, project, hours);
            report.add(line);
        }

        return report;
    }
}