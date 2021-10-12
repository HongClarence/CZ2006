package com.example.cz2006.ui.settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cz2006.R;
import com.example.cz2006.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private int num;
    private double percentage;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();


        ListView listSettings = binding.listSettings;
        String[] settings = getActivity().getResources().getStringArray(R.array.settings);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this.getActivity(), R.layout.listview_item, settings);
        listSettings.setAdapter(arr);
        listSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                String selectedFromList = (String) (listSettings.getItemAtPosition(myItemInt));

                switch(selectedFromList)
                {
                    case "Water Limit":
                        setLimit(0);
                        break;

                    case "Electricity Limit":
                        setLimit(1);
                        break;
                }
            }
        });

        ListView listLogout = binding.listLogout;
        String[] strlogout = {"Log Out"};
        ArrayAdapter<String> logout = new ArrayAdapter<String>(this.getActivity(), R.layout.listview_item, strlogout);
        listLogout.setAdapter(logout);

        return root;
    }

    public void setLimit(int type){
        Button ok_button,cancel_button;
        SeekBar setPercentage;
        TextView title, percent, units, dollarAmount;
        EditText inputAmount;
        double rate=1.50;

        dialogBuilder = new AlertDialog.Builder(this.getActivity());
        final View popup=getLayoutInflater().inflate(R.layout.fragment_limit,null);
        dialogBuilder.setView(popup);
        inputAmount=(EditText) popup.findViewById(R.id.inputAmount);
        dollarAmount=(TextView) popup.findViewById(R.id.dollarAmount);
        inputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = inputAmount.getText().toString();
                num = Integer.parseInt(input);
                dollarAmount.setText("$"+ (int) num * rate);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        title = popup.findViewById(R.id.text_title);
        percent=(TextView) popup.findViewById(R.id.percent);
        units=(TextView) popup.findViewById(R.id.text_units);
        setPercentage=(SeekBar)popup.findViewById(R.id.setPercentage);

        if(type == 0) {
            title.setText("Water Limit");
            units.setText("Litres");
        }
        else {
            title.setText("Electricity Limit");
            units.setText("kWh");
        }

        setPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                percent.setText("Notify me when I reach "+ i +"%" + "("+ num * i/100 + units.getText() + ") "+" of limit");
                percentage = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ok_button=(Button) popup.findViewById(R.id.ok_button);
        cancel_button=(Button) popup.findViewById(R.id.cancel_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsViewModel.setLimits(type, Double.parseDouble(inputAmount.getText().toString()), percentage);
                dialog.dismiss();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog=dialogBuilder.create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(1000, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}