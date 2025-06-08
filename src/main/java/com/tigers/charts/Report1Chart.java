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
import java.util.HashMap;

public class Report1Chart implements Chart {

    public void generateChart(DataCollector dataCollector) throws IOException {

        String outputPath = "chart/report1.png";

        Map<String, Double> employeeHoursMap = new HashMap<>();
        for (Task task : dataCollector.getTasks()) {
            String employee = task.getEmployee();
            double hours = task.getHours();
            employeeHoursMap.put(employee, employeeHoursMap.getOrDefault(employee, 0.0) + hours);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : employeeHoursMap.entrySet()) {
            dataset.addValue(entry.getValue(), "Godziny", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Raport godzin pracy",
                "Pracownik",
                "Liczba godzin",
                dataset
        );

        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        ChartUtils.saveChartAsPNG(outputFile, barChart, 800, 600);
//        System.out.println("Wykres zapisany do: " + outputFile.getAbsolutePath());
    }
}