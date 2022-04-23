package com.example.sdidebartesti;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsFragment extends Fragment {

    Spinner languages;
    Button enableSettings;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        languages = view.findViewById(R.id.spinnerLanguage);
        enableSettings = view.findViewById(R.id.button_enable_settings);
        ArrayList<String> languagesArraylist= new ArrayList<String>();
        languagesArraylist.add("suomi");
        languagesArraylist.add("english");
        String[] languagesArray = new String[languagesArraylist.size()];
        languagesArraylist.toArray(languagesArray);
        ArrayAdapter<String> languagesArrayAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, languagesArray);
        languages.setAdapter(languagesArrayAdapter);

        enableSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedLanguage = languages.getSelectedItem().toString();
                if (selectedLanguage == "Suomi"){
                    Locale locale = new Locale("fi");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getResources().updateConfiguration(config,getResources().getDisplayMetrics());

                }
                if (selectedLanguage == "English"){
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getResources().updateConfiguration(config,getResources().getDisplayMetrics());

                }

                Intent i = getContext().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}
