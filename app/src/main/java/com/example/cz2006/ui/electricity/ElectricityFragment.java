package com.example.cz2006.ui.electricity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cz2006.R;
import com.example.cz2006.Versions;
import com.example.cz2006.VersionsAdapter;
import com.example.cz2006.databinding.FragmentElectricityBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class ElectricityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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

    private RecyclerView recyclerView;
    private List<Versions> versionsList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        electricityViewModel = new ViewModelProvider(this).get(ElectricityViewModel.class);
        binding = FragmentElectricityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        color  = getActivity().getResources().getIntArray(R.array.color);
        spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.duration, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Log.i("AAAAAAAAAAAAA", spinner.getSelectedItem().toString());

        chart = binding.barChartView;
        textTotal = binding.textTotal;
        textUsed = binding.textUsed;
        textRemaining = binding.textRemaining;
        textCost = binding.textCost;
        recyclerView = binding.recyclerView;

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

        initData();
        setRecyclerView();

        return root;
    }

    private ArrayList<BarEntry> dataValues(){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(0, new float[]{2, 2, 2, 0, 5}));
        dataVals.add(new BarEntry(1, new float[]{3, 3, 3, 1, 5}));
        dataVals.add(new BarEntry(2, new float[]{4, 4, 4, 2, 5}));
        return dataVals;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String text = parent.getItemAtPosition(pos).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void setRecyclerView() {
        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
        recyclerView.setAdapter(versionsAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData() {

        versionsList = new ArrayList<>();

        versionsList.add(new Versions("Shower", "$20", "40 Litres", "You are using 29% more water in the shower than the average for your house type!\n\nYour average showering time is 15minutes.\n\nTurn off the shower tap when you are applying soap and shampoo!\n\nYou can cut down your shower time by 5 minutes to save 5 litres of water."));
        versionsList.add(new Versions("Washing Machine", "$20", "40 Litres", "Just don\'t wash clothes at home bro, just go singapore river wash."));
        versionsList.add(new Versions("Kitchen Sink", "$12", "12 litres", "Go downstairs use toilet don\'t use the house one"));
        versionsList.add(new Versions("Misc", "$200", "40 litres", "hahahahha water go brrrrrr"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}