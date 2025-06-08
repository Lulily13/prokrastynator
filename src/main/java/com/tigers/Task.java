package com.tigers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {
    private String year;
    private String month;
    private String day;
    private String employee;
    private String taskName;
    private Collection<String> tags = new ArrayList<>();
    private String category;
    private double hours;
    private String projectName;

    public Task(String year, String day, String month, String employee, double hours, String projectName, String taskName) {
        this.year = year;
        this.day = day;
        this.month = month;
        this.employee = employee;
        this.hours = hours;
        this.projectName = projectName;
        this.taskName = taskName;

        Pattern categoryPattern = Pattern.compile("\\[([^)]+)\\]");
        Matcher categoryMatcher = categoryPattern.matcher(taskName);
        this.category = categoryMatcher.find() ? categoryMatcher.group(1) : null;

        Pattern tagPattern = Pattern.compile("#(\\w+)");
        Matcher tagMatcher = tagPattern.matcher(taskName);
        List<String> tags = new ArrayList<>();
        while (tagMatcher.find()) {
            tags.add(tagMatcher.group(1));
        }
        this.tags = tags;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
