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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    ListView movieSearchListView;
    TextInputEditText movieSearch;
    Button showAll;
    TextView notification;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // As the SearchFragment is opened, the user is able to search a movie by its name and all the search results will be shown to the user by a listView
    // There the user can select any movie he/she wants and the software will open a movieInfo fragment to that movie. Pretty much same as PersonSearchFragment.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MovieManager mm = MovieManager.getInstance();
        movieSearchListView = view.findViewById(R.id.movieListView);
        movieSearch = view.findViewById(R.id.movieSearchBar);
        showAll = view.findViewById(R.id.buttonShowAll);
        notification = view.findViewById(R.id.textViewNotification);
        notification.setText("Näytetään kaikki elokuvat:");
        ArrayList<String> MoviesArrayList = new ArrayList<String>();
        String[] stringMovies = new String[MoviesArrayList.size()];
        ArrayAdapter<String> moviesAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, MoviesArrayList);

        for (int i = 0; i < mm.MOVIES.size(); i++) {
            MoviesArrayList.add(mm.MOVIES.get(i).getTitle());
            MoviesArrayList.toArray(stringMovies);
            movieSearchListView.setAdapter(moviesAdapter);
            moviesAdapter.notifyDataSetChanged();
            movieSearch.setText(null);
        }
        movieSearchListView.setAdapter(moviesAdapter);
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
        movieSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String searchBarText = movieSearch.getText().toString();
                    MoviesArrayList.clear();
                    for (int i = 0; i < mm.MOVIES.size(); i++) {
                        if (mm.MOVIES.get(i).getTitle().contains(searchBarText)) {
                            MoviesArrayList.add(mm.MOVIES.get(i).getTitle());
                            MoviesArrayList.toArray(stringMovies);
                            movieSearchListView.setAdapter(moviesAdapter);
                            moviesAdapter.notifyDataSetChanged();

                        }
                    }
                    notification.setText("Näytetään tulokset haulle: '"+ searchBarText +"'");
                    return true;
                }
                return false;
            }
        });

        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoviesArrayList.clear();
                for (int i = 0; i < mm.MOVIES.size(); i++) {
                    MoviesArrayList.add(mm.MOVIES.get(i).getTitle());
                    MoviesArrayList.toArray(stringMovies);
                    movieSearchListView.setAdapter(moviesAdapter);
                    moviesAdapter.notifyDataSetChanged();
                    movieSearch.setText(null);
                    notification.setText("Näytetään kaikki elokuvat:");
                }
            }
        });
    }
}
