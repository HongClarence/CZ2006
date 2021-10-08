package com.example.cz2006.ui.electricity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ElectricityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ElectricityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}