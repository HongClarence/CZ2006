package com.example.cz2006.ui.main;

import android.os.Bundle;
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
import com.example.cz2006.adapters.VersionsAdapter;
import com.example.cz2006.classes.Response;
import com.example.cz2006.databinding.FragmentElectricityBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;

public class ElectricityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private CombinedViewModel combinedViewModel;
    private FragmentElectricityBinding binding;

    private BarChart chart;
    int[] color = new int[] {R.color.lightBlue, R.color.purple, R.color.teal_700, R.color.gray};
    int[] test;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        combinedViewModel = new ViewModelProvider(this).get(CombinedViewModel.class);
        binding = FragmentElectricityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        test  = getActivity().getResources().getIntArray(R.array.color);
        Spinner spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.duration, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        chart = binding.barChartView;

        TextView textTotal = binding.textTotal;
        TextView textUsed = binding.textUsed;
        TextView textRemaining = binding.textRemaining;
        TextView textCost = binding.textCost;
        RecyclerView recyclerView = binding.recyclerView;

        combinedViewModel.getResponse(1).observe(getViewLifecycleOwner(), new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                textTotal.setText(response.getSummary().getTotal());
                textUsed.setText(response.getSummary().getUsed());
                textRemaining.setText(response.getSummary().getRemaining());
                textCost.setText(response.getSummary().getCost());

                BarDataSet barDataSet = new BarDataSet(response.getBarEntryList(), "test");
                barDataSet.setColors(color);
                BarData barData = new BarData(barDataSet);
                barData.setDrawValues(false);
                chart.setData(barData);

                VersionsAdapter versionsAdapter = new VersionsAdapter(response.getVersionsList());
                recyclerView.setAdapter(versionsAdapter);
            }

        });
        return root;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String text = parent.getItemAtPosition(pos).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}