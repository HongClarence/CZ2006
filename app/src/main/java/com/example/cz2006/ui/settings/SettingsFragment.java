package com.example.cz2006.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cz2006.R;
import com.example.cz2006.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    private ListView listSettings;
    private String[] settings;

    private ListView listLogout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();


        listSettings = binding.listSettings;
        settings = getActivity().getResources().getStringArray(R.array.settings);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this.getActivity(), R.layout.listview_item, settings);
        listSettings.setAdapter(arr);

        listLogout = binding.listLogout;
        String[] strlogout = {"Log Out"};
        ArrayAdapter<String> logout = new ArrayAdapter<String>(this.getActivity(), R.layout.listview_item, strlogout);
        listLogout.setAdapter(logout);


        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                listLogout.setTag(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}