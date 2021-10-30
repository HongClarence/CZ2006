package com.example.cz2006.ui.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cz2006.R;
import com.example.cz2006.adapters.VersionsAdapter;
import com.example.cz2006.classes.ElectricityData;
import com.example.cz2006.classes.Response;
import com.example.cz2006.classes.Versions;
import com.example.cz2006.databinding.FragmentElectricityBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElectricityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private SharedViewModel sharedViewModel;
    private FragmentElectricityBinding binding;

    private List<ElectricityData> hourlyElectricity;
    private List<ElectricityData> dailyElectricity;
    private List<ElectricityData> monthlyElectricity;

    private List<String> xHourly;
    private List<String> xDaily;
    private List<String> xMonthly;

    private BarChart chart;
    private BarDataSet barDataSet;
    private BarData barData;
    private List<BarEntry> barEntryList = new ArrayList<BarEntry>();

    int[] color = new int[] {Color.RED, Color.BLACK, Color.BLUE, Color.YELLOW, Color.GREEN};
    int[] test;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
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

        xHourly = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.hourly)));
        xDaily = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.daily)));
        xMonthly = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.monthly)));
        chart = binding.barChartView;
        configureChart();

        TextView textTotal = binding.textTotal;
        TextView textUsed = binding.textUsed;
        TextView textRemaining = binding.textRemaining;
        TextView textCost = binding.textCost;
        RecyclerView recyclerView = binding.recyclerView;

        sharedViewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                textTotal.setText("$" + (int)(response.getSummary().getWaterCost() + response.getSummary().getElectricityCost()));
                textUsed.setText((int)response.getSummary().getElectricityUsage() + "kWh");
                textRemaining.setText((int)response.getSummary().getElectricityRemaining() + "kWh");
                textCost.setText("$" + (int)response.getSummary().getElectricityCost());

                hourlyElectricity = response.getHourlyElectricity();
                dailyElectricity = response.getDailyElectricity();
                monthlyElectricity = response.getMonthlyElectricity();

                for(ElectricityData e: hourlyElectricity) {
                    barEntryList.add(new BarEntry(e.getDate(), new float[]{
                            e.getAirCon(), e.getFridge(), e.getTv(), e.getWaterHeater(), e.getMisc()
                    }));
                }
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xHourly));
                configureAndUpdate();

                //Get current month and put in get(), hardcoded as 11 for demo
                float airconUsage = monthlyElectricity.get(11).getAirCon();
                float fridgeUsage = monthlyElectricity.get(11).getFridge();
                float tvUsage = monthlyElectricity.get(11).getTv();
                float waterHeaterUsage = monthlyElectricity.get(11).getWaterHeater();
                float miscUsage = monthlyElectricity.get(11).getMisc();
                float rate = 0;

                switch(response.getUserData().getElectricitySupplier()) {
                    case "SupplierX":
                        rate = (float) 0.258;
                        break;
                    case "SupplierY":
                        rate = (float) 0.28;
                        break;
                    case "SupplierZ":
                        rate = (float) 0.3;
                        break;
                }

                List<Versions> versionsList = new ArrayList<>();
                versionsList.add(new Versions("Air Con", "$" + (int)(airconUsage * rate), (int)airconUsage + " kWh", "You are using 29% more water in the shower than the average for your house type!\n\nYour average showering time is 15minutes.\n\nTurn off the shower tap when you are applying soap and shampoo!\n\nYou can cut down your shower time by 5 minutes to save 5 litres of water."));
                versionsList.add(new Versions("Fridge", "$" + (int)(fridgeUsage * rate), (int)fridgeUsage + " kWh", "Just don't wash clothes at home bro, just go singapore river wash."));
                versionsList.add(new Versions("TV", "$" + (int)(tvUsage * rate), (int)tvUsage + " kWh", "Go downstairs use toilet don't use the house one"));
                versionsList.add(new Versions("Water Heater", "$" + (int)(waterHeaterUsage * rate), (int)waterHeaterUsage + " kWh", "hahahahha water go brrrrrr"));
                versionsList.add(new Versions("Misc", "$" + (int)(miscUsage * rate), (int)miscUsage + " kWh", "hahahahha water go brrrrrr"));
                VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
                recyclerView.setAdapter(versionsAdapter);

                int used = (int) response.getSummary().getElectricityUsage();
                int remaining = (int) response.getSummary().getElectricityRemaining();
                sendNotification(used * 100 / (used + remaining));
            }
        });
        return root;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if(hourlyElectricity == null)
            return;

        String range = parent.getItemAtPosition(pos).toString();
        barEntryList.clear();
        List<ElectricityData> electricityDataList = null;
        switch(range) {
            case "Hourly":
                electricityDataList = hourlyElectricity;
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xHourly));
                break;

            case "Daily":
                electricityDataList = dailyElectricity;
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xDaily));
                break;

            case "Monthly":
                electricityDataList = monthlyElectricity;
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xMonthly));
                break;
        }
        for(ElectricityData e: electricityDataList)
            barEntryList.add(new BarEntry(e.getDate(), new float[]{
                    e.getAirCon(), e.getFridge(), e.getTv(), e.getWaterHeater(), e.getMisc()
            }));
        configureAndUpdate();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void configureChart() {
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);

        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisRight().setAxisMinimum(0);
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setLabelCount(6);
    }

    public void configureAndUpdate() {
        barDataSet = new BarDataSet(barEntryList, "");
        barData = new BarData(barDataSet);
        barDataSet.setColors(color);
        barDataSet.setStackLabels(new String[]{"Air Con", "Fridge", "TV", "Water Heater", "Misc"});
        barData.setDrawValues(false);
        chart.setData(barData);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    public void sendNotification(int i) {
        if(i < 25)
            return;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "1", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getActivity(), "1")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Electricity Limit Alert")
                .setContentText("You have used " + i + "% of your electricity limit!")
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getActivity());
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}