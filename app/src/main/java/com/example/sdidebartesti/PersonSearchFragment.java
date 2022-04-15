package com.example.sdidebartesti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class PersonSearchFragment extends Fragment {
    ListView personSearchList;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_search, container, false);
    }

    // As the PersonSearchFragment is opened, the software takes the name the user gives it by the inputBar
    // and searches the archive for each movie that includes the searched person's name. Then it puts the movies to an arrayList and transfers it back here.
    // Then the listView shows the wanted movies and the user can click specific movie that will open movieInfo -fragment for that movie.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> MoviesByPersonsArrayList = new ArrayList<String>();
        MoviesByPersonsArrayList.add("Elokuva 1");
        MoviesByPersonsArrayList.add("Elokuva 2");
        MoviesByPersonsArrayList.add("Elokuva 3");
        MoviesByPersonsArrayList.add("Elokuva 4");
        MoviesByPersonsArrayList.add("Elokuva 5");
        MoviesByPersonsArrayList.add("Elokuva 6");
        MoviesByPersonsArrayList.add("Elokuva 7");
        personSearchList = view.findViewById(R.id.movieListView);
        String[] stringMoviesByPersonsArray = new String[MoviesByPersonsArrayList.size()];
        MoviesByPersonsArrayList.toArray(stringMoviesByPersonsArray);
        ArrayAdapter<String> moviesByPersonsAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringMoviesByPersonsArray);
        personSearchList.setAdapter(moviesByPersonsAdapter);
        personSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemName = (String) personSearchList.getItemAtPosition(i);
                System.out.println("******** HOMMA TOIMII *******");
                System.out.println(itemName);
                MovieInfoFragment movieInfoFragment = new MovieInfoFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, movieInfoFragment).addToBackStack(null).commit();
                Bundle bundle = new Bundle();
                bundle.putString("key", itemName);
                movieInfoFragment.setArguments(bundle);
            }
        });
    }

}
