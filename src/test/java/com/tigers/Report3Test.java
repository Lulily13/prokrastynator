package com.tigers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;


import static org.junit.jupiter.api.Assertions.*;


class Report3Test {


    private DataCollector dataCollector;


    @BeforeEach
    void setUp() {
        dataCollector = new DataCollector();


        Collection<Task> tasks = Arrays.asList(
                new Task("2024", "01", "01", "Nowak_Anna", 5.0, "Projekt A", "Dev"),
                new Task("2024", "02", "01", "Nowak_Anna", 3.0, "Projekt B", "QA"),
                new Task("2024", "02", "01", "Nowak_Anna", 2.0, "Projekt A", "Review"),
                new Task("2024", "03", "01", "Kowalski_Jan", 4.0, "Projekt C", "Testy")
        );


        dataCollector.setTasks(tasks);
    }




    //Czy raport poprawnie przetwarza jeden wpis z danymi pracownika?
    @Test
    void testPrepareReport_singleEntry() {
        DataCollector dc = new DataCollector();
        dc.setTasks(Collections.singletonList(
                new Task("2024", "01", "01", "Nowak_Anna", 5.0, "Projekt A", "Dev")
        ));


        Report3 report = new Report3("Nowak_Anna");
        Collection<String> result = report.prepareReport(dc);


        List<String> expected1 = Collections.singletonList("1. 2024-01 - Projekt A - 5,00 godzin");
        List<String> expected2 = Collections.singletonList("1. 2024-01 - Projekt A - 5.00 godzin");
        try {
            assertEquals(expected1, new ArrayList<>(result));
        } catch (org.opentest4j.AssertionFailedError e) {
            assertEquals(expected2, new ArrayList<>(result));
        }
    }






    //Czy godziny sumują się, gdy są z tego samego miesiąca i projektu?
    @Test
    void testPrepareReport_sumSameMonthProject() {
        DataCollector dc = new DataCollector();
        dc.setTasks(Arrays.asList(
                new Task("2024", "01", "02", "Nowak_Anna", 3.0, "Projekt A", "Dev"),
                new Task("2024", "01", "02", "Nowak_Anna", 2.0, "Projekt A", "QA")


        ));


        Report3 report = new Report3("Nowak_Anna");
        Collection<String> result = report.prepareReport(dc);


        List<String> expected1 = Collections.singletonList("1. 2024-02 - Projekt A - 5,00 godzin");
        List<String> expected2 = Collections.singletonList("1. 2024-02 - Projekt A - 5.00 godzin");
        try {
            assertEquals(expected1, new ArrayList<>(result));
        } catch (org.opentest4j.AssertionFailedError e) {
            assertEquals(expected2, new ArrayList<>(result));
        }
    }




    //Czy raport zawiera wszystkie projekty z różnych miesięcy, posortowane alfabetycznie?
    @Test
    void testPrepareReport_multipleProjectsAndMonths() {
        DataCollector dc = new DataCollector();
        dc.setTasks(Arrays.asList(
                new Task("2024", "01", "01", "Nowak_Anna", 5.0, "Projekt A", "Zadanie 1"),
                new Task("2024", "02", "05", "Nowak_Anna", 2.0, "Projekt A", "Zadanie 2"),
                new Task("2024", "02", "10", "Nowak_Anna", 3.0, "Projekt B", "Zadanie 3")
        ));

        Report3 report = new Report3("Nowak_Anna");
        Collection<String> result = report.prepareReport(dc);

        List<String> expected1 = Arrays.asList(
                "1. 2024-01 - Projekt A - 5,00 godzin",
                "2. 2024-05 - Projekt A - 2,00 godzin",
                "3. 2024-10 - Projekt B - 3,00 godzin"
        );
        List<String> expected2 = Arrays.asList(
                "1. 2024-01 - Projekt A - 5.00 godzin",
                "2. 2024-05 - Projekt A - 2.00 godzin",
                "3. 2024-10 - Projekt B - 3.00 godzin"
        );

        try {
            assertEquals(expected1, new ArrayList<>(result));
        } catch (org.opentest4j.AssertionFailedError e) {
            assertEquals(expected2, new ArrayList<>(result));
        }
    }


    //Czy Report3 prawidłowo filtruje zadania? uwzględnia tylko te przypisane do pracownika Kowalski_Jan, ignorując zadania innych osób?
    @Test
    void testPrepareReport_otherEmployeeIgnored() {
        Report3 report = new Report3("Kowalski_Jan");
        Collection<String> result = report.prepareReport(dataCollector);


        List<String> expected1 = Collections.singletonList("1. 2024-01 - Projekt C - 4,00 godzin");
        List<String> expected2 = Collections.singletonList("1. 2024-01 - Projekt C - 4.00 godzin");
        try {
            assertEquals(expected1, new ArrayList<>(result));
        } catch (org.opentest4j.AssertionFailedError e) {
            assertEquals(expected2, new ArrayList<>(result));
        }
    }

    //czy Report3 działa poprawnie, gdy nie ma żadnych danych wejściowych – czyli gdy lista zadań (tasks) jest pusta?
    @Test
    void testPrepareReport_emptyDataCollector() {
        DataCollector emptyCollector = new DataCollector();
        emptyCollector.setTasks(Collections.emptyList());


        Report3 report = new Report3("Nowak_Anna");
        Collection<String> result = report.prepareReport(emptyCollector);


        assertTrue(result.isEmpty());
    }


    //czy raport generowany przez Report3 jest posortowany alfabetycznie po nazwie projektu, gdy daty są takie same?
    @Test
    void testPrepareReport_sortedByDateAndProject() {
        Collection<Task> tasks = new ArrayList<>(dataCollector.getTasks());
        tasks.add(new Task("2024", "01", "01", "Nowak_Anna", 2.0, "AAAAProjekt", "Planowanie"));
        dataCollector.setTasks(tasks);

        Report3 report = new Report3("Nowak_Anna");
        List<String> result = new ArrayList<>(report.prepareReport(dataCollector));

        assertTrue(result.get(0).contains("AAAAProjekt"));
    }
}

