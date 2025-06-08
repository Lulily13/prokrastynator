package com.tigers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelMiner {

    private String targetYear;

    public DataCollector getDataCollector() {
        return dataCollector;
    }

    private DataCollector dataCollector = new DataCollector();

    public String[] przejdzPrzezFolder(File folder) {
        List<String> sciezki = new ArrayList<>();
        if (folder == null || !folder.exists()) {
            System.out.println("Folder nie istnieje.");
            return new String[0];
        }

        if (!folder.isDirectory()) {
            System.out.println(folder.getAbsolutePath() + " nie jest folderem.");
            return new String[0];
        }

        File[] pliki = folder.listFiles();
        if (pliki == null) {
            System.out.println("Brak plików w folderze.");
            return new String[0];
        }

        for (File plik : pliki) {
            if (!plik.isDirectory() && plik.getName().endsWith(".xls")) {
                sciezki.add(plik.getAbsolutePath());

            } else {
                String[] podfolderSciezki = przejdzPrzezFolder(plik);
                for (String s : podfolderSciezki) {
                    sciezki.add(s);
                }
            }
        }

        return sciezki.toArray(new String[0]);
    }

    public void readExcel(String excelFilePath) {
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
                    if (row == null) continue;

                    // Ensure the row has exactly 3 cells
                    if (row.getPhysicalNumberOfCells() != 3) continue;
                    try {
                        Cell dateCell = row.getCell(0);
                        Cell categoryCell = row.getCell(1);
                        Cell hoursCell = row.getCell(2);

                        if (dateCell == null || categoryCell == null || hoursCell == null) continue;

                        // Validate the cell types
                        if (!DateUtil.isCellDateFormatted(dateCell)) continue;
                        if (categoryCell.getCellType() != CellType.STRING) continue;
                        if (hoursCell.getCellType() != CellType.NUMERIC) continue;

                        // Extract and process data
                        date = dateCell.getDateCellValue();
                        category = categoryCell.getStringCellValue();
                        hours = hoursCell.getNumericCellValue();

                        if (hours <= 0) {
                            continue;
                        }

                        Calendar Cal = Calendar.getInstance();
                        Cal.setTime(date);
                        year = String.valueOf(Cal.get(Calendar.YEAR));
                        month = String.valueOf(Cal.get(Calendar.MONTH) + 1);
                        day = String.valueOf(Cal.get(Calendar.DAY_OF_MONTH));
                        String projectName = workbook.getSheetName(i);
                        String fileNameWithExtension = excelFilePath.substring(excelFilePath.lastIndexOf("/") + 1);
                        fileName = fileNameWithExtension.replaceFirst("[.][^.]+$", "");

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
                    catch (Exception e) {
                        // Skip row on any parsing error
                        System.out.println("Niepoprawne dane w rzędzie: " + j + " w arkuszu: '" + sheet.getSheetName() + "'" + " w pliku: " + excelFilePath);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runMiner(String path, String targetYear) {
        String filepath = path + '/' + targetYear;
        String[] paths = przejdzPrzezFolder(new File(filepath));

        for (String path1 : paths) {
            readExcel(path1);
        }

    }

}