package com.tigers.charts;

import com.tigers.DataCollector;
import com.tigers.Task;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Report5Chart implements Chart{

    private String employee;
    private String project;
    private String category;

    public Report5Chart(String employee, String project, String category) {
        this.employee = employee;
        this.project = project;
        this.category = category;
    }

    public void generateChart(DataCollector dataCollector) throws IOException {
        // Agregowanie danych godzinowych na zadaniach
        Map<String, Double> taskSummary = new TreeMap<>();
        String outputPath = "chart/report5.png";  // Ścieżka do pliku

        // Przetwarzanie danych w zależności od filtrów
        for (Task task : dataCollector.getTasks()) {
            if (employee != null && !task.getEmployee().equalsIgnoreCase(employee)) continue;
            if (project != null && !task.getProjectName().equalsIgnoreCase(project)) continue;
            if (category != null && (task.getCategory() == null || !task.getCategory().equalsIgnoreCase(category))) continue;

            String taskName = task.getTaskName();
            double hours = task.getHours();

            taskSummary.merge(taskName, hours, Double::sum);  // Sumowanie godzin dla każdego zadania
        }

        // Tworzymy dataset do wykresu słupkowego
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : taskSummary.entrySet()) {
            String taskName = entry.getKey();
            double hours = entry.getValue();

            dataset.addValue(hours, "Godziny", taskName);  // Dodajemy zadanie i godziny do wykresu
        }

        // Tworzymy wykres słupkowy
        JFreeChart barChart = ChartFactory.createBarChart(
                "Liczba godzin na zadaniach - " + employee + " (" + project + ", " + category + ")",  // Tytuł wykresu
                "Zadanie",                                      // Oś X
                "Liczba godzin",                                // Oś Y
                dataset,                                        // Dataset
                org.jfree.chart.plot.PlotOrientation.VERTICAL,  // Orientacja wykresu
                true,                                           // Pokazanie legendy
                true,                                           // Pokazanie tooltips
                false                                           // Pokazanie URL
        );

        // Zapisanie wykresu do pliku
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        ChartUtils.saveChartAsPNG(outputFile, barChart, 800, 600);
    }
}
