package com.example.cz2006.ui.water;

import android.graphics.Color;
import android.os.Bundle;
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
import com.example.cz2006.databinding.FragmentWaterBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class WaterFragment extends Fragment {

    private WaterViewModel waterViewModel;
    private FragmentWaterBinding binding;

    private Spinner spinner;

    private BarChart chart;
    int[] color = new int[] {Color.RED, Color.BLACK, Color.BLUE};

    private TextView textTotal;
    private TextView textUsed;
    private TextView textRemaining;
    private TextView textCost;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        waterViewModel = new ViewModelProvider(this).get(WaterViewModel.class);
        binding = FragmentWaterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


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

        waterViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTotal.setText("$50");
                textUsed.setText("50Litres");
                textRemaining.setText("50Litres");
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
        dataVals.add(new BarEntry(0, new float[]{2,2,2}));
        dataVals.add(new BarEntry(1, new float[]{3,3,3}));
        dataVals.add(new BarEntry(2, new float[]{4,4,4}));
        return dataVals;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}