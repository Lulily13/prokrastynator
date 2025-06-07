package com.tigers;

import java.util.*;

public class Report2 implements Segregator {

    private String selectedYear;

    public Report2(String year) {
        this.selectedYear = year;
    }

    @Override
    public Collection<String> prepareReport(DataCollector dataCollector) {
        Map<String, Double> projectHours = new HashMap<>();

        for (Task task : dataCollector.getTasks()) {
            if (selectedYear.equals(task.getYear())) {
                String project = task.getProjectName();
                double hours = task.getHours();

                Double currentHours = projectHours.get(project);
                if (currentHours == null) {
                    currentHours = 0.0;
                }

                projectHours.put(project, currentHours + hours);
            }
        }

        List<String> report = new ArrayList<>();
        report.add("Lp | Projekt | Liczba godzin");

        int lp = 1;
        for (String project : projectHours.keySet()) {
            double hours = projectHours.get(project);
            report.add(lp + " | " + project + " | " + hours + "h");
            lp++;
        }

        return report;
    }
}