package com.example.cz2006.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.widget.Toast;

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
import com.example.cz2006.classes.Response;
import com.example.cz2006.databinding.FragmentElectricityBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class ElectricityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private CombinedViewModel combinedViewModel;
    private FragmentElectricityBinding binding;

    private BarChart chart;
    private BarDataSet barDataSet;
    private BarData barData;
    private List<BarEntry> barEntryList = new ArrayList<BarEntry>();
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
        configureChart();

        TextView textTotal = binding.textTotal;
        TextView textUsed = binding.textUsed;
        TextView textRemaining = binding.textRemaining;
        TextView textCost = binding.textCost;
        RecyclerView recyclerView = binding.recyclerView;

        combinedViewModel.getResponse(1).observe(getViewLifecycleOwner(), new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                textTotal.setText("$" + response.getSummary().getTotal());
                textUsed.setText(response.getSummary().getUsed() + "kWh");
                textRemaining.setText(response.getSummary().getRemaining() + "kWh");
                textCost.setText("$" + response.getSummary().getCost());

                barEntryList = response.getBarEntryList();
                VersionsAdapter versionsAdapter = new VersionsAdapter(response.getVersionsList());
                recyclerView.setAdapter(versionsAdapter);

                int used = response.getSummary().getUsed();
                int remaining = response.getSummary().getRemaining();
                sendNotification(used * 100 / (used + remaining));
            }

        });
        return root;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String range = parent.getItemAtPosition(pos).toString();
        //Toast.makeText(parent.getContext(), range, Toast.LENGTH_SHORT).show();
        barEntryList.clear();

        switch(range) {
            case "Daily":
                barEntryList.add(new BarEntry(1, new float[]{2, 2, 2, 0}));
                barEntryList.add(new BarEntry(2, new float[]{3, 3, 3, 1}));
                barEntryList.add(new BarEntry(3, new float[]{4, 4, 4, 2}));
                break;

            case "Weekly":
                barEntryList.add(new BarEntry(4, new float[]{2, 2, 2, 0}));
                barEntryList.add(new BarEntry(5, new float[]{3, 3, 3, 1}));
                barEntryList.add(new BarEntry(6, new float[]{4, 4, 4, 2}));
                break;

            case "Monthly":
                barEntryList.add(new BarEntry(7, new float[]{2, 2, 2, 0}));
                barEntryList.add(new BarEntry(8, new float[]{3, 3, 3, 1}));
                barEntryList.add(new BarEntry(9, new float[]{4, 4, 4, 2}));
                break;
        }
        barDataSet = new BarDataSet(barEntryList, "");
        barData = new BarData(barDataSet);
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

        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisRight().setAxisMinimum(0);
        chart.getXAxis().setGranularity(1f);
    }

    public void configureAndUpdate() {
        barDataSet.setColors(color);
        barDataSet.setStackLabels(new String[]{"AA", "AAA", "AAA", "AAA"});
        barData.setDrawValues(false);
        chart.setData(barData);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    public void sendNotification(int i) {
        if(i < 25)
            return;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("AAA", "AAA", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getActivity(), "AAA")
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