package com.tigers;

import java.util.*;

public class Report4 implements Segregator {

    private final String employee;

    public Report4(String employee) {
        this.employee = employee;
    }

    @Override
    public Collection<String> prepareReport(DataCollector dataCollector) {
        Map<String, Double> projectHours = new TreeMap<>();
        double totalHours = 0.0;

        for (Task task : dataCollector.getTasks()) {
            if (task.getEmployee().equalsIgnoreCase(employee)) {
                String project = task.getProjectName();
                double hours = task.getHours();
                projectHours.merge(project, hours, Double::sum);
                totalHours += hours;
            }
        }

        List<String> report = new ArrayList<>();
        int counter = 1;

        for (Map.Entry<String, Double> entry : projectHours.entrySet()) {
            String project = entry.getKey();
            double projectHour = entry.getValue();
            double percent = (totalHours == 0.0) ? 0.0 : (projectHour / totalHours) * 100.0;

            String line = String.format("%d. %s - %.2f%%", counter++, project, percent);
            report.add(line);
        }

        return report;
    }
}
