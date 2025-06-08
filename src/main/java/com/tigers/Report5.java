package com.tigers;

import java.util.*;

public class Report5 implements Segregator {

    private final String employee;
    private final String project;
    private final String category;

    public Report5(String employee, String project, String category) {
        this.employee = employee;
        this.project = project;
        this.category = category;
    }

    @Override
    public Collection<String> prepareReport(DataCollector dataCollector) {
        Map<String, Double> taskSummary = new TreeMap<>();

        for (Task task : dataCollector.getTasks()) {
            if (employee != null && !task.getEmployee().equalsIgnoreCase(employee)) continue;

            if (project != null && !task.getProjectName().equalsIgnoreCase(project)) continue;

            if (category != null && (task.getCategory() == null || !task.getCategory().equalsIgnoreCase(category))) continue;

            String taskName = task.getTaskName();
            double hours = task.getHours();

            taskSummary.merge(taskName, hours, Double::sum);
        }

        List<String> report = new ArrayList<>();
        report.add("Lp | Zadanie | Liczba godzin");

        int lp = 1;
        for (Map.Entry<String, Double> entry : taskSummary.entrySet()) {
            report.add(lp++ + " | " + entry.getKey() + " | " + String.format("%.2f", entry.getValue()) + " h");
        }

        return report;
    }
}
