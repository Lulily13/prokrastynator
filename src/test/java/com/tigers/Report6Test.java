package com.tigers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class Report6Test {
    private DataCollector dataCollector;


    @BeforeEach
    void setUp() {
        dataCollector = new DataCollector();
    }


    @Test
    void shouldFilterBySpecificTag() {
        // Given
        Task t1 = createMockTask("2023", List.of("critical", "urgent"));
        Task t2 = createMockTask("2023", List.of("critical"));
        Task t3 = createMockTask("2023", List.of("maintenance"));


        dataCollector.setTasks(new ArrayList<>(List.of(t1, t2, t3)));


        // When
        Report6 report = new Report6()
                .withDateRange(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31))
                .withTagFilter("critical");


        Collection<String> result = report.prepareReport(dataCollector);


        // Then
        assertTrue(result.contains("1 | #critical | 2"));
        assertEquals(1, result.size() - 1);
    }


    @Test
    void shouldShowAllTagsWhenNoFilter() {
        // Given
        Task t1 = createMockTask("2023", List.of("critical"));
        Task t2 = createMockTask("2023", List.of("urgent"));


        dataCollector.setTasks(new ArrayList<>(List.of(t1, t2)));


        // When
        Report6 report = new Report6()
                .withDateRange(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));


        Collection<String> result = report.prepareReport(dataCollector);


        // Then
        assertTrue(result.contains("1 | #critical | 1"));
        assertTrue(result.contains("2 | #urgent | 1"));
        assertEquals(2, result.size() - 1);
    }


    private Task createMockTask(String year, List<String> tags) {
        Task task = Mockito.mock(Task.class);
        when(task.getYear()).thenReturn(year);
        when(task.getMonth()).thenReturn("1");
        when(task.getDay()).thenReturn("1");
        when(task.getTags()).thenReturn(new HashSet<>(tags));
        return task;
    }
}

