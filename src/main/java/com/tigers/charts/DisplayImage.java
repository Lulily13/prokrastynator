package com.tigers.charts;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DisplayImage {

    private String imagePath;

    // Konstruktor przyjmujący ścieżkę do obrazu
    public DisplayImage(String imagePath) {
        this.imagePath = imagePath;
    }

    // Metoda wyświetlająca obraz
    public void displayImageFun() {
        // Utworzenie okna (JFrame)
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File file = new File(imagePath);
        if (!file.exists()) {
            System.out.println("Błąd: Plik obrazu nie istnieje w ścieżce: " + imagePath);
            return;
        } else {
//            System.out.println("Plik obrazu znaleziony: " + imagePath);
        }

        try {
            // Załadowanie obrazu
//            System.out.println("Ładowanie obrazu z ścieżki: " + imagePath);
            ImageIcon imageIcon = new ImageIcon(imagePath);

            // Sprawdzanie, czy obraz został poprawnie załadowany
            if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.out.println("Błąd: Obraz nie został poprawnie załadowany.");
                return;
            }

            // Utworzenie komponentu JLabel z obrazem
            JLabel label = new JLabel(imageIcon);
            frame.getContentPane().add(label, BorderLayout.CENTER);

            // Ustawienie rozmiaru okna do rozmiaru obrazu
            frame.pack();
            frame.setVisible(true);

//            System.out.println("Obraz został załadowany i okno jest widoczne.");

        } catch (Exception e) {
            System.out.println("Błąd podczas ładowania obrazu: " + e.getMessage());
            e.printStackTrace();  // Szczegóły błędu
        }
    }
}
