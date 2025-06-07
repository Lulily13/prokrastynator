package com.tigers;

public class Main {
    public static void main(String[] args) {
        String inputPath = null;

        for (int i = 0; i < args.length; i++) {
            if ("--input".equals(args[i]) && i + 1 < args.length) {
                inputPath = args[i + 1];
                i++;
            }
        }

        if (inputPath == null) {
            System.out.println("Błąd: Nie podano ścieżki (--input).");
            return;
        }

        ExcelMiner miner = new ExcelMiner();

        System.out.println("Podaj rok: ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String year = scanner.nextLine();

        miner.runMiner(inputPath, year);

        UserInputHandler uiHandler = new UserInputHandler();
        uiHandler.startInteractiveMenu(inputPath, miner.getDataCollector());
    }
}
