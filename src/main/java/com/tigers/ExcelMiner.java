package com.tigers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class ExcelMiner {

    public DataCollector getDataCollector() {
        return dataCollector;
    }

    private DataCollector dataCollector = new DataCollector();

    public void readExcel(String excelFilePath) {
//        String excelFilePath = "/home/students/m/i/mikostrz/Documents/reporter-dane/2012/01/Kowalski_Jan.xls";
        double hours = 0;
        String category = "";
        String year = "";
        String day = "";
        String month = "";
        String fileName = "";
        Date date = new Date();

        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new HSSFWorkbook(fis)) {

            int numberOfSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);

                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                    Row row = sheet.getRow(j);
                    String projectName = workbook.getSheetName(i);
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                category = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    date = cell.getDateCellValue();
                                } else {
                                    hours = cell.getNumericCellValue();
                                }
                                break;
                            case BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + "\t");
                                break;
                            default:
                                System.out.print("UNKNOWN\t");
                        }
                        Calendar Cal = Calendar.getInstance();
                        Cal.setTime(date);
                        year = String.valueOf(Cal.get(Calendar.YEAR));
                        month = String.valueOf(Cal.get(Calendar.MONTH) + 1);
                        day = String.valueOf(Cal.get(Calendar.DAY_OF_MONTH));
                        String fileNameWithExtension = excelFilePath.substring(excelFilePath.lastIndexOf("/") + 1);
                        fileName = fileNameWithExtension.replaceFirst("[.][^.]+$", "");

                    }
                    if (hours > 0) {
                        Task task = new Task(
                                year,
                                day,
                                month,
                                fileName,
                                hours,
                                projectName,
                                category
                        );
                        Collection<Task> tasks = this.dataCollector.getTasks();
                        tasks.add(task);
                        this.dataCollector.setTasks(tasks);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}