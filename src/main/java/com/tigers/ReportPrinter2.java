package com.tigers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ReportPrinter2 {

    public void printFancyTable(Collection<String> lines) {
        List<String[]> rows = new ArrayList<>();
        int[] colWidth = null;

        for (String line : lines) {
            String[] cols = line.split("\\|");
            for (int i = 0; i < cols.length; i++) cols[i] = cols[i].trim();
            rows.add(cols);

            if (colWidth == null) colWidth = new int[cols.length];
            for (int i = 0; i < cols.length; i++)
                colWidth[i] = Math.max(colWidth[i], cols[i].length());
        }

        StringBuilder sep = new StringBuilder("+");
        for (int w : colWidth) sep.append("-".repeat(w + 2)).append("+");
        System.out.println(sep);

        for (int r = 0; r < rows.size(); r++) {
            StringBuilder line = new StringBuilder("|");
            String[] cols = rows.get(r);
            for (int c = 0; c < cols.length; c++)
                line.append(" ").append(padRight(cols[c], colWidth[c])).append(" |");
            System.out.println(line);
            if (r == 0) System.out.println(sep);   // separator po nagłówku
        }
        System.out.println(sep);
    }

    public String padRight(String txt, int len) {
        return String.format("%-" + len + "s", txt);
    }
}
