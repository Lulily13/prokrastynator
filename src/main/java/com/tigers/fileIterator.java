package com.tigers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class fileIterator {

    public static String[] przejdzPrzezFolder(File folder) {
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
            if (!plik.isDirectory()) {
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

    public static void main(String[] args) {
        File folder = new File("/mnt/workspaces/private/prokrastynator/dane");
        String[] wszystkiePliki = przejdzPrzezFolder(folder);

        for (String path : wszystkiePliki) {
            System.out.println(path);
        }

    }
}

