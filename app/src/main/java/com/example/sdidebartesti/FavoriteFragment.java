package com.example.sdidebartesti;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    ListView favoriteMoviesListView;
    TextInputEditText favoriteSearchBar;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteMoviesListView = view.findViewById(R.id.favoriteListView);
        favoriteSearchBar = view.findViewById(R.id.favoriteSearchBar);
        MovieManager mm = MovieManager.getInstance();
        // ****************************************************************************************************************************Making the listview working
        ArrayList<String> FavoriteMoviesIDArrayList = new ArrayList<String>();
        // TODO! WIHTORI TOHON ARRAYLISTIIN ^^^ käyttäjän favoritemovie ID:T!!!!

        for(int i = 0; i < FavoriteMoviesIDArrayList.size(); i++){        // Changes Movie ID's to Movie Names

        }
        ArrayList<String> FavoriteMoviesNameArrayList = new ArrayList<>();
        String[] stringFavoriteMovies = new String[FavoriteMoviesNameArrayList.size()];
        FavoriteMoviesNameArrayList.toArray(stringFavoriteMovies);
        ArrayAdapter<String> moviesAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringFavoriteMovies);
        favoriteMoviesListView.setAdapter(moviesAdapter);



        //*****************************************************************************************************************************Search from the listView
        favoriteSearchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String searchBarText = favoriteSearchBar.getText().toString();
                    /*
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
                     */
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }
}
