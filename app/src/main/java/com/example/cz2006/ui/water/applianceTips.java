package com.example.cz2006.ui.water;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.example.cz2006.R;

public class applianceTips extends Fragment {

    RecyclerView recyclerView;
    List<Versions> versionsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cardtips, container, false);

        recyclerView = recyclerView.findViewById(R.id.recyclerView);

        initData();
        setRecyclerView();

        return view;
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
}
