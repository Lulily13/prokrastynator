package com.tigers.charts;

import com.tigers.DataCollector;
import com.tigers.Task;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Report2Chart implements Chart{

    private String selectedYear;

    public Report2Chart(String year) {
        this.selectedYear = year;
    }

    public void generateChart(DataCollector dataCollector) throws IOException {
        Map<String, Double> projectHours = new HashMap<>();
        String outputPath = "chart/report2.png";

        for (Task task : dataCollector.getTasks()) {
            if (selectedYear.equals(task.getYear())) {
                String project = task.getProjectName();
                double hours = task.getHours();
                projectHours.put(project, projectHours.getOrDefault(project, 0.0) + hours);
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : projectHours.entrySet()) {
            dataset.addValue(entry.getValue(), "Godziny", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Godziny pracy wg projektu - Rok " + selectedYear,
                "Projekt",
                "Liczba godzin",
                dataset
        );

        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        ChartUtils.saveChartAsPNG(outputFile, barChart, 800, 600);
    }

}