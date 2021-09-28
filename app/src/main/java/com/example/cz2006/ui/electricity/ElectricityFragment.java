package com.example.cz2006.ui.electricity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cz2006.R;
import com.example.cz2006.databinding.FragmentElectricityBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ElectricityFragment extends Fragment {

    private ElectricityViewModel electricityViewModel;
    private FragmentElectricityBinding binding;

    private Spinner spinner;

    private BarChart chart;
    int[] test = new int[] {Color.RED, Color.BLACK, Color.BLUE};

    int[] color;

    private TextView textTotal;
    private TextView textUsed;
    private TextView textRemaining;
    private TextView textCost;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        electricityViewModel =
                new ViewModelProvider(this).get(ElectricityViewModel.class);
        binding = FragmentElectricityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Log.i("AAAAAAAAAAAAA", String.valueOf(test[1]));

        color  = getActivity().getResources().getIntArray(R.array.color);
        spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.duration, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        chart = binding.barChartView;
        textTotal = binding.textTotal;
        textUsed = binding.textUsed;
        textRemaining = binding.textRemaining;
        textCost = binding.textCost;

        electricityViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTotal.setText("$50");
                textUsed.setText("50kWh");
                textRemaining.setText("50kWh");
                textCost.setText("$50");
            }
        });

        BarDataSet barDataSet = new BarDataSet(dataValues(), "test");
        barDataSet.setColors(color);
        BarData barData = new BarData(barDataSet);
        barData.setDrawValues(false);
        chart.setData(barData);

        return root;
    }

    private ArrayList<BarEntry> dataValues(){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(0, new float[]{2, 2, 2, 0, 5}));
        dataVals.add(new BarEntry(1, new float[]{3, 3, 3, 1, 5}));
        dataVals.add(new BarEntry(2, new float[]{4, 4, 4, 2, 5}));
        return dataVals;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}