package com.tigers;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if (args.length < 2 || !args[0].equals("--input")) {
            System.out.println("Użycie: java -jar prokrastynator.jar --input <ścieżka_do_katalogu>");
            return;
        }

        String inputPath = args[1];
        File folder = new File(inputPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Błąd: Podana ścieżka nie istnieje lub nie jest katalogiem.");
            return;
        }

        System.out.println("Wczytano katalog z danymi: " + inputPath);


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

        int reportNumber = -1;
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
            System.out.println("Błąd: Rok musi być czterocyfrowy, np. 2024.");
            return;
        }

        int year = Integer.parseInt(yearInput);

        String employee = null;
        String project = null;
        String prefix = null;

        switch (reportNumber) {
            case 1:
                System.out.print("Podaj imię i nazwisko pracownika: ");
                employee = scanner.nextLine();
                break;

            case 2:
                System.out.print("Podaj nazwę projektu: ");
                project = scanner.nextLine();
                break;

            case 3:
            case 4:
                System.out.print("Podaj imię i nazwisko pracownika: ");
                employee = scanner.nextLine();
                break;

            case 5:
                System.out.print("Podaj nazwę projektu lub nazwisko pracownika: ");
                project = scanner.nextLine(); // lub employee
                System.out.print("Czy chcesz filtrować tylko konkretne typy zadań (np. tylko 'bugfix')? (tak/nie): ");
                String filterChoice = scanner.nextLine();
                if (filterChoice.equalsIgnoreCase("tak")) {
                    System.out.print("Podaj prefix (np. bugfix): ");
                    prefix = scanner.nextLine();
                }
                break;

            case 6:
                System.out.print("Podaj nazwę projektu lub nazwisko pracownika: ");
                project = scanner.nextLine(); // lub employee
                break;
        }

        System.out.println("\n----------------------------------------");
        System.out.println("Podsumowanie wyboru:");
        System.out.println("- Katalog danych: " + inputPath);
        System.out.println("- Numer raportu: " + reportNumber);
        System.out.println("- Rok: " + year);
        if (employee != null) System.out.println("- Pracownik: " + employee);
        if (project != null) System.out.println("- Projekt: " + project);
        if (prefix != null) System.out.println("- Filtrowanie po zadaniach typu: " + prefix);
        System.out.println("Przygotowywanie raportu... (logika do zaimplementowania)");
        System.out.println("----------------------------------------");

        // TODO: przetwarzanie plików z inputPath i generowanie raportu
    }
}

