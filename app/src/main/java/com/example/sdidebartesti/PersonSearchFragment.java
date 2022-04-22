package com.example.sdidebartesti;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PersonSearchFragment extends Fragment {
    ListView personSearchList;
    TextInputEditText searchBar;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_search, container, false);
    }

    // As the PersonSearchFragment is opened, the software takes the name the user gives it by the inputBar
    // and searches the archive for each movie that includes the searched person's name. Then it puts the movies to an arrayList and transfers it back here.
    // Then the listView shows the wanted movies and the user can click specific movie that will open movieInfo -fragment for that movie.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MovieManager mm = MovieManager.getInstance();
        ArrayList<String> MoviesByPersonsArrayList = new ArrayList<String>();
        MoviesByPersonsArrayList.add("Elokuva 1");
        MoviesByPersonsArrayList.add("Elokuva 2");
        personSearchList = view.findViewById(R.id.movieListView);
        searchBar = view.findViewById(R.id.moviePersonSearchBar);
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
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String searchBarText = searchBar.getText().toString();
                    MoviesByPersonsArrayList.clear();
                    for (int i = 0 ; i < mm.MOVIES.size(); i++){
                        if (mm.MOVIES.get(i).getCastMember(searchBarText)){
                            MoviesByPersonsArrayList.add(mm.MOVIES.get(i).getTitle());
                            String[] stringMoviesByPersonsArray = new String[MoviesByPersonsArrayList.size()];
                            MoviesByPersonsArrayList.toArray(stringMoviesByPersonsArray);
                            ArrayAdapter<String> moviesByPersonsAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringMoviesByPersonsArray);
                            personSearchList.setAdapter(moviesByPersonsAdapter);
                        }
                    }

                    return true;
                }
                return false;
            }
        });
    }

}
