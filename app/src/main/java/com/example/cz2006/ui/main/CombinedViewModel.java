package com.example.cz2006.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cz2006.classes.Response;
import com.example.cz2006.classes.Summary;
import com.example.cz2006.classes.Versions;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class CombinedViewModel extends ViewModel {

    private MutableLiveData<Response> waterResponse;
    private MutableLiveData<Response> electricityResponse;

    public LiveData<Response> getResponse(int i) {
        if(waterResponse == null) {
            waterResponse = new MutableLiveData<>();
            electricityResponse = new MutableLiveData<>();
            getData();
        }

        if(i == 0)
            return waterResponse;
        else
            return electricityResponse;
    }

    private void getData() {
        Summary waterSummary = new Summary("$100", "50Litres", "50Litres", "$50");
        Summary electricitySummary = new Summary("$100", "50kWh", "50kWh", "$50");

        List<BarEntry> barEntryList = new ArrayList<BarEntry>();
        barEntryList.add(new BarEntry(1, new float[]{2, 2, 2, 0}));
        barEntryList.add(new BarEntry(2, new float[]{3, 3, 3, 1}));
        barEntryList.add(new BarEntry(3, new float[]{4, 4, 4, 2}));

        List<Versions> versionsList = new ArrayList<Versions>();
        versionsList.add(new Versions("Shower", "$20", "40 Litres", "You are using 29% more water in the shower than the average for your house type!\n\nYour average showering time is 15minutes.\n\nTurn off the shower tap when you are applying soap and shampoo!\n\nYou can cut down your shower time by 5 minutes to save 5 litres of water."));
        versionsList.add(new Versions("Washing Machine", "$20", "40 Litres", "Just don't wash clothes at home bro, just go singapore river wash."));
        versionsList.add(new Versions("Kitchen Sink", "$12", "12 litres", "Go downstairs use toilet don't use the house one"));
        versionsList.add(new Versions("Misc", "$200", "40 litres", "hahahahha water go brrrrrr"));

        waterResponse.setValue(new Response(waterSummary, barEntryList, versionsList));
        electricityResponse.setValue(new Response(electricitySummary, barEntryList, versionsList));
    }
}