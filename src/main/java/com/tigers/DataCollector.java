package com.tigers;

import java.util.ArrayList;
import java.util.Collection;

public class DataCollector {

    private Collection<Task> tasks = new ArrayList<>();

    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }

}
