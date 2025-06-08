
package com.tigers;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputPath = "/mnt/workspaces/private/prokrastynator/dane";

//
//        for (int i = 0; i < args.length; i++) {
//            if ("--input".equals(args[i]) && i + 1 < args.length) {
//                inputPath = args[i + 1];
//                i++;
//            }
//        }
//
//
//        if (inputPath == null) {
//            System.out.println("Błąd: Nie podano ścieżki (--input).");
//            return;
//        }


        ExcelMiner miner = new ExcelMiner();


        UserInputHandler uiHandler = new UserInputHandler();
        uiHandler.startInteractiveMenu(inputPath);

    }
}

