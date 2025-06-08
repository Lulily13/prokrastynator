package com.tigers;

import java.util.*;

public class Raport1 implements Segregator {

    public List<String> prepareReport(DataCollector dataCollector) {
        Map<String, Double> employeeHoursMap = new HashMap<>();

        for (Task task : dataCollector.getTasks()) {
            String employee = task.getEmployee();
            double hours = task.getHours();
            employeeHoursMap.put(employee, employeeHoursMap.getOrDefault(employee, 0.0) + hours);

        }

        List<String> report = new ArrayList<>();
        int counter = 1;
        for (Map.Entry<String, Double> entry : employeeHoursMap.entrySet()) {
            String line = String.format("%d | %s | %.2f godzin", counter++, entry.getKey(), entry.getValue());
            report.add(line);

        }

        return report;

    }

}
