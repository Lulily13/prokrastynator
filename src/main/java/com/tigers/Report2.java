package com.tigers;

import java.util.*;

public class Report2 implements Segregator {

    private final String selectedYear;

    public Report2(String year) {
        this.selectedYear = year;
    }

    @Override
    public Collection<String> prepareReport(DataCollector dataCollector) {
        Map<String, Double> projectHours = new HashMap<>();

        /* ---------------- główna pętla po zadaniach ---------------- */
        for (Task task : dataCollector.getTasks()) {

            /* (1) – filtrujemy po wybranym roku */
            if (!selectedYear.equals(task.getYear())) continue;

            /* (2) – pomijamy puste / null-owe nazwy projektu */
            String project = task.getProjectName();
            if (project == null || project.trim().isEmpty()) continue;

            double hours = task.getHours();
            projectHours.merge(project, hours, Double::sum);
        }

        /* ---------------- budujemy wynik ---------------- */
        List<String> report = new ArrayList<>();
        report.add("Lp | Projekt | Liczba godzin");

        int lp = 1;
        for (Map.Entry<String, Double> e : projectHours.entrySet()) {
            report.add(lp++ + " | " + e.getKey() + " | " + e.getValue() + "h");
        }

        return report;
    }
}