package com.tigers;

import java.util.List;

public class RaportPrinter1 {

    public void printReport(List<String> reportLines) {
        for (String line : reportLines) {
            System.out.println(line);
        }
    }
}
