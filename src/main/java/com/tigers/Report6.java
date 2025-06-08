package com.tigers;

import java.time.LocalDate;
import java.util.*;

public class Report6 implements Segregator {
    private LocalDate startDate;
    private LocalDate endDate;
    private String targetTag;

    public Report6 withDateRange(LocalDate start, LocalDate end) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException("Data końcowa nie może być wcześniejsza niż początkowa");
        }
        this.startDate = start;
        this.endDate = end;
        return this;
    }

    public Report6 withTagFilter(String tag) {
        this.targetTag = tag != null ? tag.toLowerCase() : null;
        return this;
    }

    @Override
    public Collection<String> prepareReport(DataCollector dataCollector) {
        Map<String, Integer> tagCounts = countAllTags(dataCollector);
        return formatResults(tagCounts);
    }

    private Map<String, Integer> countAllTags(DataCollector collector) {
        Map<String, Integer> tagCounter = new HashMap<>();
        for (Task task : collector.getTasks()) {
            if (!isTaskInDateRange(task)) continue;

            for (String tag : task.getTags()) {
                String normalizedTag = tag.toLowerCase();
                if (shouldCountTag(normalizedTag)) {
                    tagCounter.put(normalizedTag, tagCounter.getOrDefault(normalizedTag, 0) + 1);
                }
            }
        }
        return tagCounter;
    }

    private boolean shouldCountTag(String tag) {
        return targetTag == null || tag.equals(targetTag);
    }

    private boolean isTaskInDateRange(Task task) {
        try {
            LocalDate taskDate = LocalDate.of(
                    Integer.parseInt(task.getYear()),
                    Integer.parseInt(task.getMonth()),
                    Integer.parseInt(task.getDay())
            );
            return (startDate == null || !taskDate.isBefore(startDate)) &&
                    (endDate == null || !taskDate.isAfter(endDate));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Collection<String> formatResults(Map<String, Integer> tagCounts) {
        List<String> lines = new ArrayList<>();
        lines.add("Lp | Tag | Liczba wystąpień");

        List<Map.Entry<String, Integer>> sortedEntries = tagCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList();

        int lp = 1;
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            lines.add(String.format("%d | #%s | %d",
                    lp++,
                    entry.getKey(),
                    entry.getValue()));
        }

        return lines;
    }
}