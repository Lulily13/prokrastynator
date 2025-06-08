package com.tigers;

import java.util.*;

public class Report4 implements Segregator {

    private final String selectedEmployee;
    private final String selectedYear;

    public Report4(String selectedEmployee, String selectedYear) {
        this.selectedEmployee = selectedEmployee;
        this.selectedYear = selectedYear;
    }

    @Override
    public Collection<String> prepareReport(DataCollector dataCollector) {
        Map<String, Double> projectHours = new HashMap<>();
        double totalHours = 0;

        for (Task task : dataCollector.getTasks()) {
            if (selectedEmployee.equalsIgnoreCase(task.getEmployee()) &&
                    selectedYear.equals(task.getYear())) {

                String project = task.getProjectName();
                double hours = task.getHours();

                projectHours.put(project, projectHours.getOrDefault(project, 0.0) + hours);
                totalHours += hours;
            }
        }

        List<String> report = new ArrayList<>();
        report.add("Lp | Projekt     | Udział %");

        int lp = 1;
        for (Map.Entry<String, Double> entry : projectHours.entrySet()) {
            double percentage = (entry.getValue() / totalHours) * 100;
            String line = String.format("%-2d | %-10s | %.2f%%", lp++, entry.getKey(), percentage);
            report.add(line);
        }

        return report;
    }
}