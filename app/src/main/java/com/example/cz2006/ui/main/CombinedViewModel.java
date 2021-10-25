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

    private MutableLiveData<Response> response;

    public LiveData<Response> getResponse(int i) {
        if(response == null) {
            response = new MutableLiveData<>();
            getData(i);
        }
        return response;
    }

    private void getData(int i) {
        Summary summary;
        if(i == 0)
            summary = new Summary(100, 50, 50, 50);
        else
            summary = new Summary(100, 50, 50, 50);

        List<BarEntry> barEntryList = new ArrayList<>();

        List<Versions> versionsList = new ArrayList<Versions>();
        versionsList.add(new Versions("Shower", "$20", "40 Litres", "You are using 29% more water in the shower than the average for your house type!\n\nYour average showering time is 15minutes.\n\nTurn off the shower tap when you are applying soap and shampoo!\n\nYou can cut down your shower time by 5 minutes to save 5 litres of water."));
        versionsList.add(new Versions("Washing Machine", "$20", "40 Litres", "Just don't wash clothes at home bro, just go singapore river wash."));
        versionsList.add(new Versions("Kitchen Sink", "$12", "12 litres", "Go downstairs use toilet don't use the house one"));
        versionsList.add(new Versions("Misc", "$200", "40 litres", "hahahahha water go brrrrrr"));

        response.setValue(new Response(summary, barEntryList, versionsList));
    }
}