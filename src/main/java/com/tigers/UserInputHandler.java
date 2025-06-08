package com.tigers;

import com.tigers.charts.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class UserInputHandler {

    public void startInteractiveMenu(String inputPath) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("           Prokrastynator v1.0");
        System.out.println("========================================");
        System.out.println("Wybierz numer raportu, który chcesz wygenerować:");
        System.out.println("1 - Ile godzin przepracowali wszyscy pracownicy w danym roku");
        System.out.println("2 - Ile godzin poświęcono na wszystkie projekty w danym roku");
        System.out.println("3 - Jak pracownik rozkładał swój czas nad projektami w kolejnych miesiącach");
        System.out.println("4 - Jakie projekty zajmowały największą część czasu danego pracownika (procentowo)");
        System.out.println("5 - Szczegółowy raport: nad jakimi zadaniami pracował pracownik lub projekt (opcjonalnie tylko np. 'bugfixy')");
        System.out.println("6 - Jakie etykiety (np. #important, #feedback) były najczęściej używane w projektach lub przez pracownika");
        System.out.print("Wybór (1–6): ");

        int reportNumber;
        try {
            reportNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Błąd: Wprowadź liczbę całkowitą z zakresu 1–6.");
            return;
        }

        if (reportNumber < 1 || reportNumber > 6) {
            System.out.println("Błąd: Nieprawidłowy numer raportu.");
            return;
        }

        System.out.print("Podaj rok, którego dotyczy raport (np. 2023): ");
        String yearInput = scanner.nextLine();
        if (!yearInput.matches("\\d{4}")) {
            System.out.println("Błąd: Rok musi być czterocyfrowy.");
            return;
        }
        String year = yearInput;

        ExcelMiner miner = new ExcelMiner();
        miner.runMiner(inputPath, year);
        DataCollector dataCollector = miner.getDataCollector();

        String employee = null;
        String project = null;
        String prefix = null;

        switch (reportNumber) {
            case 3:
            case 4:
                System.out.print("Podaj imię i nazwisko pracownika (w formacie Nazwisko_Imię): ");
                employee = scanner.nextLine();
                break;
            case 5:
                System.out.print("Checesz filtrować po kliencie (y/n): ");
                String inputEmpl = scanner.nextLine();
                String employeeCheck = inputEmpl.toLowerCase();
                if (employeeCheck.equals("y")) {
                    System.out.print("Podaj imię i nazwisko pracownika (w formacie Nazwisko_Imię): ");
                    employee = scanner.nextLine();
                }

                else{ employee = null;
                }

                System.out.print("Checesz filtrować po projekcie (y/n): ");
                String inputProj = scanner.nextLine();
                String projCheck = inputProj.toLowerCase();
                if (projCheck.equals("y")) {
                    System.out.print("Podaj nazwę projektu: ");
                    project = scanner.nextLine();
                }

                else{ project = null;
                }

                System.out.print("Checesz filtrować po Kategoriach (y/n): ");
                String inputCat = scanner.nextLine();
                String catCheck = inputCat.toLowerCase();
                if (catCheck.equals("y")) {
                    System.out.print("Podaj nazwę projektu: ");
                    prefix = scanner.nextLine();
                }

                else{ prefix = null;
                }


                break;

            case 6:
                System.out.print("Podaj nazwę projektu lub pracownika: ");
                String tagTarget = scanner.nextLine();
                if (tagTarget.toLowerCase().startsWith("proj:")) {
                    project = tagTarget.substring(5);
                } else {
                    employee = tagTarget;
                }
                break;
        }

        // Wybierz i uruchom konkretny raport
        Segregator report = null;
        switch (reportNumber) {
            case 1:
                report = new Report1();
                break;
            case 2:
                report = new Report2(year);
                break;
            case 3:
                report = new Report3(employee);
                break;
            case 4:
                report = new Report4(employee);
                break;
            case 5:
                report = new Report5(employee,project, prefix);
                break;
//            case 6:
//                report = new Report6();
//                break;

            default:
                System.out.println("Podaj numer z zakresu 1-6.");
                return;
        }

        System.out.println("\n----------------------------------------");
        System.out.println("Podsumowanie wyboru:");
        System.out.println("- Katalog danych: " + inputPath);
        System.out.println("- Numer raportu: " + reportNumber);
        System.out.println("- Rok: " + year);
        if (employee != null) System.out.println("- Pracownik: " + employee);
        if (project != null) System.out.println("- Projekt: " + project);
        if (prefix != null) System.out.println("- Filtrowanie po zadaniach typu: " + prefix);
        System.out.println("----------------------------------------");

        Collection<String> reportResult = report.prepareReport(dataCollector);

        System.out.println("\n===== RAPORT =====");
        GeneralReportPrinter fancy = new GeneralReportPrinter();
        fancy.printFancyTable(reportResult);
        System.out.println("==================");

        System.out.println("Chcesz wygenerować wykres (y/n):");
        String chartCheck = scanner.nextLine();

        if (chartCheck.equals("y")) {
            Chart chart = null;
            switch (reportNumber){
                case 1:
                    chart = new Report1Chart();

                    break;
                case 2:
                    chart = new Report2Chart(year);
                    break;

                case 3:
                    chart = new Report3Chart(employee);
                    break;

                case 4:
                    chart = new Report4Chart(employee);
                    break;

                case 5:
                    chart = new Report5Chart(employee,project,prefix);
                    break;
            }
            chart.generateChart(dataCollector);
        }

    }
}



