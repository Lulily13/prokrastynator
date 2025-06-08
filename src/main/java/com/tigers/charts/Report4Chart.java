package com.tigers.charts;

import com.tigers.DataCollector;
import com.tigers.Task;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Report4Chart implements Chart{

    private String selectedEmployee;

    public Report4Chart(String employee) {
        this.selectedEmployee = employee;
    }

    public void generateChart(DataCollector dataCollector) throws IOException {
        Map<String, Double> projectHours = new HashMap<>();
        double totalHours = 0.0;
        String outputPath = "chart/report4_" + selectedEmployee + ".png";  // ścieżka względna

        // Agregowanie danych dla danego pracownika
        for (Task task : dataCollector.getTasks()) {
            if (task.getEmployee().equalsIgnoreCase(selectedEmployee)) {
                String project = task.getProjectName();
                double hours = task.getHours();
                projectHours.put(project, projectHours.getOrDefault(project, 0.0) + hours);
                totalHours += hours;
            }
        }

        // Jeśli pracownik nie ma żadnych godzin w tym roku, kończymy generowanie wykresu
        if (totalHours == 0.0) {
            System.out.println("Brak danych dla pracownika " + selectedEmployee);
            return;
        }

        // Tworzenie datasetu wykresu kołowego
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : projectHours.entrySet()) {
            String project = entry.getKey();
            double projectHoursValue = entry.getValue();
            double percent = (projectHoursValue / totalHours) * 100.0;

            // Dodajemy dane do datasetu
            dataset.setValue(project, percent);
        }

        // Tworzenie wykresu kołowego
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Procentowy udział godzin - " + selectedEmployee,  // Tytuł wykresu
                dataset,                                         // Dane
                true,                                             // Pokazanie legendy
                true,                                             // Pokazanie tooltips
                false                                             // Pokazanie URL
        );

        // Uzyskanie wykresu kołowego
        PiePlot plot = (PiePlot) pieChart.getPlot();

        // Dodanie etykiet z procentami
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0} = {1} godz. ({2}%)"
        );
        plot.setLabelGenerator(labelGenerator);

        // Zapisanie wykresu do pliku
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        ChartUtils.saveChartAsPNG(outputFile, pieChart, 800, 600);
    }
}
