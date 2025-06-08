package com.tigers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Report4Test {

    private DataCollector dataCollector;

    @BeforeEach
    void setUp() {
        dataCollector = new DataCollector();

        Collection<Task> tasks = Arrays.asList(
                new Task("2024", "01", "01", "Nowak_Anna", 5.0, "Projekt A", "Zadanie #start [Dev]"),
                new Task("2024", "02", "01", "Nowak_Anna", 3.0, "Projekt B", "Bugfix #urgent [QA]"),
                new Task("2024", "03", "01", "Nowak_Anna", 2.0, "Projekt A", "Review #important [Dev]"),
                new Task("2024", "04", "01", "Kowalski_Jan", 4.0, "Projekt C", "Testy [QA]")
        );

        dataCollector.setTasks(tasks);
    }

    @Test
    void testPrepareReport_singleEmployee_multipleProjects() {
        Report4 report = new Report4("Nowak_Anna");

        Collection<String> result = report.prepareReport(dataCollector);

        List<String> expected1 = Arrays.asList(
                "1. Projekt A - 70,00%",
                "2. Projekt B - 30,00%"
        );
        List<String> expected2 = Arrays.asList(
                "1. Projekt A - 70.00%",
                "2. Projekt B - 30.00%"
        );

        try {
            assertEquals(expected1, new ArrayList<>(result));
        } catch (org.opentest4j.AssertionFailedError e) {
            assertEquals(expected2, new ArrayList<>(result));
        }
    }

    @Test
    void testPrepareReport_caseInsensitiveEmployeeMatch() {
        Report4 report = new Report4("nowak_anna");

        Collection<String> result = report.prepareReport(dataCollector);

        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(line -> line.contains("Projekt A")));
    }

    @Test
    void testPrepareReport_employeeWithNoTasks() {
        Report4 report = new Report4("Jane_Doe");

        Collection<String> result = report.prepareReport(dataCollector);

        assertTrue(result.isEmpty());
    }

    @Test
    void testPrepareReport_zeroTotalHours() {
        DataCollector emptyCollector = new DataCollector();
        emptyCollector.setTasks(Collections.emptyList());

        Report4 report = new Report4("Nowak_Anna");

        Collection<String> result = report.prepareReport(emptyCollector);

        assertTrue(result.isEmpty());
    }

    @Test
    void testPrepareReport_projectsSortedAlphabetically() {
        Collection<Task> tasks = new ArrayList<>(dataCollector.getTasks());
        tasks.add(new Task("2024", "05", "01", "Nowak_Anna", 2.0, "AAAAProjekt", "Planowanie"));
        dataCollector.setTasks(tasks);

        Report4 report = new Report4("Nowak_Anna");
        List<String> result = new ArrayList<>(report.prepareReport(dataCollector));

        assertTrue(result.get(0).contains("AAAAProjekt"));
    }
}
