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
import android.widget.Spinner;
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


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        ListView listSettings = binding.listSettings;
        String[] settings = getActivity().getResources().getStringArray(R.array.settings);
        //String[] settings = {"Electricity Limit", "Water Limit", "Supplier"};
        int[] drawableIds = {R.drawable.ic_electricityblue, R.drawable.ic_water1, R.drawable.ic_baseline_person_24};
        //ArrayAdapter<String> arr = new ArrayAdapter<String>(this.getActivity(), R.layout.listview_item, settings);
        settingCustomAdapter adapter = new settingCustomAdapter(this.getContext(), settings, drawableIds);
        listSettings.setAdapter(adapter);
        listSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {

                switch(position)
                {
                    case 1:
                        setLimit(0);
                        break;

                    case 0:
                        setLimit(1);
                        break;
                    case 2:
                        setSupplier();
                        break;

                }
            }
        });

        ListView listLogout = binding.listLogout;
        String[] strlogout = {"Log Out"};
        int[] intlogout = {R.drawable.ic_logout};
        settingCustomAdapter logout = new settingCustomAdapter(this.getContext(), strlogout, intlogout);
        listLogout.setAdapter(logout);

        return root;
    }

    public void setLimit(int type){
        Button ok_button, cancel_button;
        TextView title, units, dollarAmount;
        EditText inputAmount;
        double rate = 1.50;

        // using dialog builder
        dialogBuilder = new AlertDialog.Builder(this.getActivity());
        final View popup = getLayoutInflater().inflate(R.layout.fragment_limit,null);
        dialogBuilder.setView(popup);

        //initialise components
        inputAmount = (EditText) popup.findViewById(R.id.inputAmount);
        dollarAmount = (TextView) popup.findViewById(R.id.dollarAmount);

        //implement TextWatcher
        inputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().trim().equals("")){
                    String input = inputAmount.getText().toString();
                    num = Integer.parseInt(input);
                    dollarAmount.setText( "$" + (int) num * rate );
                }
                else{
                    //reset amount to 0
                    dollarAmount.setText("$0");
                }
            }
        });

        //displaying title and units
        title = popup.findViewById(R.id.text_title);
        units = (TextView) popup.findViewById(R.id.text_units);

        if(type == 0) {
            title.setText("Water Limit");
            units.setText("Litres");
        }
        else {
            title.setText("Electricity Limit");
            units.setText("kWh");
        }

        // ok and cancel buttons
        ok_button = (Button) popup.findViewById(R.id.ok_button);
        cancel_button = (Button) popup.findViewById(R.id.cancel_button);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store inputAmount
                String temp=inputAmount.getText().toString();
                int value=0;
                if (!"".equals(temp)){
                    value=Integer.parseInt(temp);
                }
                dialog.dismiss();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //displaying the components
        dialog = dialogBuilder.create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(1000, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setSupplier(){

        Spinner spinnerElectricity,spinnerWater;
        TextView supplierElectricity,supplierWater;
        Button ok_button,cancel_button;

        // using dialog builder
        dialogBuilder = new AlertDialog.Builder(this.getActivity());
        final View popup = getLayoutInflater().inflate(R.layout.fragment_supplier,null);
        dialogBuilder.setView(popup);

        //initialise the components
        supplierElectricity = (TextView) popup.findViewById(R.id.supplierElectricity);
        spinnerElectricity = (Spinner) popup.findViewById(R.id.spinnerElectricity);
        supplierWater = (TextView) popup.findViewById(R.id.supplierWater);
        spinnerWater = (Spinner) popup.findViewById(R.id.spinnerWater);

        //dropdown for electricity suppliers
        String [] ElectricitySuppliers = getResources().getStringArray(R.array.ElectricitySuppliers);
        ArrayAdapter adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, ElectricitySuppliers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerElectricity.setAdapter(adapter);

        spinnerElectricity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // dropdown for water suppliers
        String [] WaterSuppliers = getResources().getStringArray(R.array.WaterSuppliers);
        ArrayAdapter adapter1 = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, WaterSuppliers);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWater.setAdapter(adapter1);

        spinnerWater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        // ok_button and cancel_button
        ok_button = (Button) popup.findViewById(R.id.ok_button);
        cancel_button = (Button) popup.findViewById(R.id.cancel_button);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedESupplier = (String) spinnerElectricity.getSelectedItem();
                String selectedWSupplier = (String) spinnerWater.getSelectedItem();
                dialog.dismiss();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //display the page
        dialog = dialogBuilder.create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(1000, 1000);
    }

}