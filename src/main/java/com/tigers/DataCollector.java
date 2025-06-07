package com.tigers;

import java.util.ArrayList;
import java.util.Collection;

public class DataCollector {
    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }

    private Collection<Task> tasks = new ArrayList<>();

}
