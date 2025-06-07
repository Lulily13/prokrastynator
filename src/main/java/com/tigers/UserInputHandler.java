package com.tigers;

import java.util.Collection;
import java.util.Scanner;
import java.util.List;

public class UserInputHandler {

    public void startInteractiveMenu(String inputPath) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("           Prokrastynator v1.0");
        System.out.println("========================================");
        System.out.println("Wybierz numer raportu, który chcesz wygenerować:");
        System.out.println("1 - Ile godzin przepracował konkretny pracownik w danym roku");
        System.out.println("2 - Ile godzin poświęcono na wybrany projekt w danym roku");
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
        miner.runMiner(inputPath, year); // załaduj dane do dataCollector
        DataCollector dataCollector = miner.getDataCollector();

        String employee = null;
        String project = null;
        String prefix = null;

        switch (reportNumber) {
            case 1:
            case 3:
            case 4:
//                System.out.print("Podaj imię i nazwisko pracownika: ");
//                employee = scanner.nextLine();
                break;

            case 2:
                System.out.print("Podaj nazwę projektu: ");
                project = scanner.nextLine();
                break;

            case 5:
                System.out.print("Podaj nazwę projektu lub pracownika: ");
                String input = scanner.nextLine();
                if (input.toLowerCase().startsWith("proj:")) {
                    project = input.substring(5);
                } else {
                    employee = input;
                }
                System.out.print("Czy chcesz filtrować tylko konkretne typy zadań (np. tylko 'bugfix')? (tak/nie): ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("tak")) {
                    System.out.print("Podaj prefix (np. bugfix): ");
                    prefix = scanner.nextLine();
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
        Segregator raport = null;
        switch (reportNumber) {
            case 1:
                raport = new Raport1();
                break;
            // Tu dodaj Raport2, Raport3, itd.
            default:
                System.out.println("Raport jeszcze niezaimplementowany.");
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

        Collection<String> raportWynik = raport.prepareReport(dataCollector);
        System.out.println("\n===== RAPORT =====");
        for (String line : raportWynik) {
            System.out.println(line);
        }
        System.out.println("==================");
    }
}



