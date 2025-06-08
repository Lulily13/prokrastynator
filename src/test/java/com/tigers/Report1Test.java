package com.tigers;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Report1Test {

    @Test
    void testPrepareReport_singleEmployeeMultipleTasks() {
        DataCollector collector = new DataCollector();

        List<Task> tasks = Arrays.asList(
                new Task("2024", "01", "06", "Anna", 2.0, "TigerProject", "Fixing bugs [Dev] #urgent"),
                new Task("2024", "02", "06", "Anna", 6.5, "TigerProject", "Implement feature [Dev] #backend")
        );

        collector.setTasks(tasks);

        Report1 report = new Report1();
        Collection<String> result = report.prepareReport(collector);

        List<String> expected = Arrays.asList(
                "Lp | Pracownik | Liczba godzin",
                "1 | Anna | 8.50h"
        );

        assertEquals(expected, new ArrayList<>(result));
    }

    @Test
    void testPrepareReport_multipleEmployees() {
        DataCollector collector = new DataCollector();

        List<Task> tasks = Arrays.asList(
                new Task("2024", "01", "06", "Anna", 3.0, "TigerProject", "Task A [Dev]"),
                new Task("2024", "02", "06", "Bartek", 4.0, "TigerProject", "Task B [QA]"),
                new Task("2024", "03", "06", "Anna", 2.0, "TigerProject", "Task C [Dev]")
        );

        collector.setTasks(tasks);

        Report1 report = new Report1();
        Collection<String> result = report.prepareReport(collector);
        List<String> reportList = new ArrayList<>(result);

        assertEquals("Lp | Pracownik | Liczba godzin", reportList.get(0));
        assertEquals(3, reportList.size());

        assertTrue(reportList.contains("1 | Anna | 5.00h") || reportList.contains("2 | Anna | 5.00h"));
        assertTrue(reportList.contains("1 | Bartek | 4.00h") || reportList.contains("2 | Bartek | 4.00h"));
    }

    @Test
    void testPrepareReport_noTasks() {
        DataCollector collector = new DataCollector();
        collector.setTasks(Collections.emptyList());

        Report1 report = new Report1();
        Collection<String> result = report.prepareReport(collector);

        List<String> expected = Collections.singletonList("Lp | Pracownik | Liczba godzin");

        assertEquals(expected, new ArrayList<>(result));
    }

    @Test
    void testPrepareReport_zeroHourTask() {
        DataCollector collector = new DataCollector();

        List<Task> tasks = Arrays.asList(
                new Task("2024", "01", "06", "Anna", 0.0, "TigerProject", "Break [Info]")
        );

        collector.setTasks(tasks);

        Report1 report = new Report1();
        Collection<String> result = report.prepareReport(collector);

        List<String> expected = Arrays.asList(
                "Lp | Pracownik | Liczba godzin",
                "1 | Anna | 0.00h"
        );

        assertEquals(expected, new ArrayList<>(result));
    }

    @Test
    void testPrepareReport_sameEmployeeDifferentCase() {
        DataCollector collector = new DataCollector();

        List<Task> tasks = Arrays.asList(
                new Task("2024", "01", "06", "Anna", 2.0, "TigerProject", "Task A"),
                new Task("2024", "02", "06", "anna", 3.0, "TigerProject", "Task B")
        );

        collector.setTasks(tasks);

        Report1 report = new Report1();
        Collection<String> result = report.prepareReport(collector);
        List<String> reportList = new ArrayList<>(result);

        assertEquals(3, reportList.size());
        assertTrue(reportList.contains("1 | Anna | 2.00h") || reportList.contains("2 | Anna | 2.00h"));
        assertTrue(reportList.contains("1 | anna | 3.00h") || reportList.contains("2 | anna | 3.00h"));
    }

    @Test
    void testPrepareReport_employeeWithNoName() {
        DataCollector collector = new DataCollector();

        List<Task> tasks = Arrays.asList(
                new Task("2024", "01", "06", "", 4.0, "TigerProject", "Unnamed Task")
        );

        collector.setTasks(tasks);

        Report1 report = new Report1();
        Collection<String> result = report.prepareReport(collector);
        List<String> expected = Arrays.asList(
                "Lp | Pracownik | Liczba godzin",
                "1 |  | 4.00h"
        );

        assertEquals(expected, new ArrayList<>(result));
    }
}
