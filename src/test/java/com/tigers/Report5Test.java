package com.tigers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Report5Test {

    private Task createTask(String employee, String project, String categoryInName, double hours) {
        // Format taskName to optionally include category in brackets and tags with #
        String taskName = (categoryInName != null ? "[" + categoryInName + "] " : "") + "DoSomething";
        return new Task("2024", "01", "06", employee, hours, project, taskName);
    }

    private DataCollector createDataCollector(Collection<Task> tasks) {
        DataCollector dc = new DataCollector();
        dc.setTasks(tasks);
        return dc;
    }

    @Test
    public void testPrepareReport_AllFiltersMatch() {
        List<Task> tasks = List.of(
                createTask("John", "Apollo", "Dev", 4.0),
                createTask("John", "Apollo", "Dev", 2.0),
                createTask("Anna", "Apollo", "Dev", 3.0) // should be filtered out
        );

        Report5 report = new Report5("John", "Apollo", "Dev");
        Collection<String> result = report.prepareReport(createDataCollector(tasks));

        assertEquals(List.of(
                "Lp | Zadanie | Liczba godzin",
                "1 | [Dev] DoSomething | 6.00 h"
        ), result);
    }

    @Test
    public void testPrepareReport_NoFilters() {
        List<Task> tasks = List.of(
                createTask("John", "Apollo", "Dev", 3.0),
                createTask("Anna", "Zeus", "QA", 2.0),
                createTask("John", "Apollo", "Dev", 1.0)
        );

        Report5 report = new Report5(null, null, null);
        Collection<String> result = report.prepareReport(createDataCollector(tasks));

        assertEquals(List.of(
                "Lp | Zadanie | Liczba godzin",
                "1 | [Dev] DoSomething | 4.00 h",
                "2 | [QA] DoSomething | 2.00 h"
        ), result);
    }

    @Test
    public void testPrepareReport_FilterByProjectOnly() {
        List<Task> tasks = List.of(
                createTask("John", "Apollo", "Dev", 2.5),
                createTask("Anna", "Zeus", "Dev", 2.0)
        );

        Report5 report = new Report5(null, "Apollo", null);
        Collection<String> result = report.prepareReport(createDataCollector(tasks));

        assertEquals(List.of(
                "Lp | Zadanie | Liczba godzin",
                "1 | [Dev] DoSomething | 2.50 h"
        ), result);
    }

    @Test
    public void testPrepareReport_NoMatchingTasks() {
        List<Task> tasks = List.of(
                createTask("Anna", "Zeus", "QA", 5.0)
        );

        Report5 report = new Report5("John", "Apollo", "Dev");
        Collection<String> result = report.prepareReport(createDataCollector(tasks));

        assertEquals(1, result.size());
        assertTrue(result.contains("Lp | Zadanie | Liczba godzin"));
    }

    @Test
    public void testPrepareReport_NullCategoryInTask() {
        Task task = new Task("2024", "01", "06", "John", 2.0, "Apollo", "SimpleTask"); // no category

        List<Task> tasks = List.of(task);
        Report5 report = new Report5("John", "Apollo", "Dev");

        Collection<String> result = report.prepareReport(createDataCollector(tasks));

        assertEquals(1, result.size());
        assertTrue(result.contains("Lp | Zadanie | Liczba godzin"));
    }

    @Test
    public void testPrepareReport_MultipleTasksSameName() {
        Task t1 = new Task("2024", "01", "06", "John", 2.0, "Apollo", "[Dev] DoSomething");
        Task t2 = new Task("2024", "01", "06", "John", 3.0, "Apollo", "[Dev] DoSomething");

        List<Task> tasks = List.of(t1, t2);
        Report5 report = new Report5("John", "Apollo", "Dev");

        Collection<String> result = report.prepareReport(createDataCollector(tasks));

        assertEquals(List.of(
                "Lp | Zadanie | Liczba godzin",
                "1 | [Dev] DoSomething | 5.00 h"
        ), result);
    }
}
