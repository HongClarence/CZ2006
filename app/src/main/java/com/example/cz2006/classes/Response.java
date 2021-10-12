package com.example.cz2006.classes;

import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class Response {
    private Summary summary;
    private List<BarEntry> barEntryList;
    private List<Versions> versionsList;

    public Response(Summary summary, List<BarEntry> barEntryList, List<Versions> versionsList) {
        this.summary = summary;
        this.barEntryList = barEntryList;
        this.versionsList = versionsList;
    }

    public Summary getSummary() {
        return summary;
    }

    public List<BarEntry> getBarEntryList() {
        return barEntryList;
    }

    public List<Versions> getVersionsList() {
        return versionsList;
    }
}
