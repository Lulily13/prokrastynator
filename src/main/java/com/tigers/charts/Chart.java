package com.tigers.charts;

import com.tigers.DataCollector;

import java.io.IOException;

public interface Chart {
    public void generateChart(DataCollector dataCollector) throws IOException;
}
