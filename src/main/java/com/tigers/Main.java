package com.tigers;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        ExcelMiner miner = new ExcelMiner();
        miner.readExcel("/home/students/m/i/mikostrz/Documents/reporter-dane/2012/01/Kowalski_Jan.xls");

        for (Task task : miner.getDataCollector().getTasks()){
            System.out.println(task.getTaskName());
            System.out.println(task.getHours());
        }

    }
}