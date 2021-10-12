package com.example.cz2006.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cz2006.classes.Limit;
import com.example.cz2006.classes.Response;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<Limit> waterLimitData;
    private MutableLiveData<Limit> electricityLimitData;

    public LiveData<Limit> getResponse(int i) {
        if (waterLimitData == null) {
            waterLimitData = new MutableLiveData<>();
            electricityLimitData = new MutableLiveData<>();
            getLimits();
        }
        if(i == 0)
            return waterLimitData;
        else
            return electricityLimitData;
    }

    public void getLimits() {
        waterLimitData.setValue(new Limit(300, 50));
        electricityLimitData.setValue(new Limit(400, 90));
    }

    public void setLimits(int i, double limit, double reminder) {
        if(i == 0)
        {
            waterLimitData.setValue(new Limit(limit, reminder));
        }
        else
        {
            electricityLimitData.setValue(new Limit(limit, reminder));
        }
    }
}