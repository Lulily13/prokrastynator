package com.tigers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Report3Chart {

    private final String employee;

    public Report3Chart(String employee) {
        this.employee = employee;
    }

    public void generateBarChart(DataCollector dataCollector) throws IOException {
        Map<String, Double> reportMap = new TreeMap<>();

        // Agregacja danych: (YYYY-MM | projekt) -> suma godzin
        for (Task task : dataCollector.getTasks()) {
            if (!task.getEmployee().equalsIgnoreCase(employee)) continue;

            String year = task.getYear();
            String month = task.getMonth();
            String project = task.getProjectName();

            String dateKey = String.format("%s-%02d", year, Integer.parseInt(month));
            String fullKey = dateKey + " | " + project;

            double hours = task.getHours();
            reportMap.merge(fullKey, hours, Double::sum);
        }


        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : reportMap.entrySet()) {
            dataset.addValue(entry.getValue(), "Godziny", entry.getKey());
        }


        String chartTitle = "Godziny pracy: " + employee;
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Data | Projekt",
                "Liczba godzin",
                dataset
        );

        String safeEmployeeName = employee.replaceAll("\\s+", "_");
        String outputDir = "charts";
        String outputFileName = String.format("report3_full_%s.png", safeEmployeeName);
        new File(outputDir).mkdirs(); // utwórz katalog jeśli nie istnieje

        File outputFile = new File(outputDir, outputFileName);
        ChartUtils.saveChartAsPNG(outputFile, barChart, 1000, 600);
        System.out.println("✅ Wykres zapisany do: " + outputFile.getAbsolutePath());
    }
}
