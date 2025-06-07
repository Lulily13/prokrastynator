package com.tigers;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Report3 {

    private final String selectedEmployee;
    private final String selectedYear;

    public Report3(String selectedEmployee, String selectedYear) {
        this.selectedEmployee = selectedEmployee;
        this.selectedYear = selectedYear;
    }

    public Collection<String> generateReport(DataCollector dataCollector) {
        //Struktura: Projekt --> Godziny
        //kluczen nazwa projektu --> String
        //wartościa suma godzin --> Double
        Map<String,Double> projectHours=new TreeMap<>(); //TreeMap do sortowania alfabetycznego


        //Pętla po wszystkich zadaniach (Task) dostępnych w DATACOLLECTOR.
        for(Task task : dataCollector.getTasks()){
            //jak zadanie nie jest z wybranego roku
            //i nie zostalo zrobione przez wybranego pracownika
            //to je pomijam
            if(!task.getEmployee().equalsIgnoreCase(selectedEmployee)) continue;
            if (!task.getYear().equals(selectedYear)) continue;


            //z zadania pobieram nazwe projektu i liczbe godzin
            String project = task.getProjectName();
            double hours = task.getHours();

            //do danego projektu dodaje godziny
            //jeśli projektu jeszcze nie ma w mapie – zostanie dodany.
            //jeśli już jest – godziny zostaną dodane do poprzednich
            projectHours.merge(project, hours, Double::sum);
        }


        //tworzę pustą listę wierszy tekstowych
        //tu zapiszemy treść raportu
        List<String> report = new ArrayList<>();
        report.add("Raport: " + selectedEmployee + " - rok " + selectedYear);
        report.add("----------------------------");


        //przechodzę przez każdy projekt z mapy projectHours
        for (Map.Entry<String, Double> entry : projectHours.entrySet()) {
            String line = String.format("Projekt %s: %.0f h", entry.getKey(), entry.getValue());
            report.add(line);
        }

        return report;
    }
}
