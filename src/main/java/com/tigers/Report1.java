package com.tigers;

import java.util.*;

public class Report1 implements Segregator {

    @Override
    public Collection<String> prepareReport(DataCollector dataCollector) {
        Map<String, Double> employeeHoursMap = new HashMap<>();

        for (Task task : dataCollector.getTasks()) {
            String employee = task.getEmployee();
            double hours = task.getHours();

            Double currentHours = employeeHoursMap.get(employee);
            if (currentHours == null) {
                currentHours = 0.0;
            }

            employeeHoursMap.put(employee, currentHours + hours);
        }

        List<String> report = new ArrayList<>();
        report.add("Lp | Pracownik | Liczba godzin");

        int lp = 1;
        for (String employee : employeeHoursMap.keySet()) {
            double hours = employeeHoursMap.get(employee);
            report.add(lp + " | " + employee + " | " + String.format(Locale.US, "%.2f", hours) + "h");
            lp++;
        }

        return report;
    }
}
