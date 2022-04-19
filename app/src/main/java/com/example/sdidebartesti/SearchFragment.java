package com.example.sdidebartesti;

import android.os.Bundle;
import android.view.KeyEvent;
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

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    ListView movieSearchListView;
    TextInputEditText movieSearch;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // As the SearchFragment is opened, the user is able to search a movie by its name and all the search results will be shown to the user by a listView
    // There the user can select any movie he/she wants and the software will open a movieInfo fragment to that movie. Pretty much same as PersonSearchFragment.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieSearchListView = view.findViewById(R.id.movieListView);
        movieSearch = view.findViewById(R.id.movieSearchBar);
        movieSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String movieSearchName = movieSearch.getText().toString();
                }
                return false;
            }
        });
        /// TÄHÄN VÄLIIN HAETAAN "movieSearchName"lla MOVIEMANAGERISTA LISTAT ELOKUVISTA
        ArrayList<String> MoviesArrayList = new ArrayList<String>(); // Tää lista korvataan listalla elokuvia, joiden nimestä löytyy HAKUSANA
        MoviesArrayList.add("Elokuva 1");
        MoviesArrayList.add("Elokuva 2");
        MoviesArrayList.add("Elokuva 3");
        MoviesArrayList.add("Elokuva 4");
        MoviesArrayList.add("Elokuva 5");
        MoviesArrayList.add("Elokuva 6");
        MoviesArrayList.add("Elokuva 7");
        String[] stringMoviesByPersonsArray = new String[MoviesArrayList.size()];
        MoviesArrayList.toArray(stringMoviesByPersonsArray);
        ArrayAdapter<String> moviesByPersonsAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringMoviesByPersonsArray);
        movieSearchListView.setAdapter(moviesByPersonsAdapter);
        movieSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemName = (String) movieSearchListView.getItemAtPosition(i);
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
