package com.example.sdidebartesti;

import static android.icu.lang.UCharacter.toLowerCase;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

public class FavoriteFragment extends Fragment {

    ListView favoriteMoviesListView;
    TextInputEditText favoriteSearchBar;
    Button showAllFavorites;
    TextView favoriteSearchNotification;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteMoviesListView = view.findViewById(R.id.favoriteListView);
        favoriteSearchBar = view.findViewById(R.id.favoriteSearchBar);
        showAllFavorites = view.findViewById(R.id.buttonShowAllFavorites);
        favoriteSearchNotification = view.findViewById(R.id.textViewNotificationFavorite);
        MovieManager mm = MovieManager.getInstance();
        // ****************************************************************************************************************************Making the listview working
        ArrayList<String> FavoriteMoviesIDArrayList = new ArrayList<String>();
        FavoriteMoviesIDArrayList.add("303322");
        FavoriteMoviesIDArrayList.add("303583");
        FavoriteMoviesIDArrayList.add("303884");
        FavoriteMoviesIDArrayList.add("303777");
        // TODO! WIHTORI TOHON ARRAYLISTIIN ^^^ käyttäjän favoritemovie ID:T!!!!
        ArrayList<String> FavoriteMoviesNameArrayList = new ArrayList<>();
        for(int i = 0; i < FavoriteMoviesIDArrayList.size(); i++){        // Changes Movie ID's to Movie Names
            if(String.valueOf(mm.MOVIES.get(i).getId()).toString().equals(FavoriteMoviesIDArrayList.get(i))){
                FavoriteMoviesNameArrayList.add(mm.MOVIES.get(i).getTitle());
            }
        }
        String[] stringFavoriteMovies = new String[FavoriteMoviesNameArrayList.size()];
        FavoriteMoviesNameArrayList.toArray(stringFavoriteMovies);
        ArrayAdapter<String> moviesAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringFavoriteMovies);
        favoriteMoviesListView.setAdapter(moviesAdapter);

        //*****************************************************************************************************************************Search from the listView
        favoriteSearchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            // ******************************************************************************************************************* Making search possible with ENTER key with hiding the software keyboard with ENTER Key
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String searchBarText = favoriteSearchBar.getText().toString();
                    FavoriteMoviesNameArrayList.clear();
                    for (int i = 0; i < mm.MOVIES.size(); i++) {
                        String comparison = toLowerCase(mm.MOVIES.get(i).getTitle());
                        if (comparison.contains(toLowerCase(searchBarText))) {
                            FavoriteMoviesNameArrayList.add(mm.MOVIES.get(i).getTitle());
                            favoriteMoviesListView.setAdapter(moviesAdapter);
                            moviesAdapter.notifyDataSetChanged();
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        }
                    }
                    favoriteSearchNotification.setText("Näytetään tulokset haulle: '"+ searchBarText +"'");
                    return true;
                }
                return false;
            }
        });
        //*********************************************************************************************************************************Open MovieInfoFragnment on ListView item click
        favoriteMoviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemName = (String) favoriteMoviesListView.getItemAtPosition(i);
                MovieInfoFragment movieInfoFragment = new MovieInfoFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, movieInfoFragment).addToBackStack(null).commit();
                Bundle bundle = new Bundle();
                bundle.putString("key", itemName);
                movieInfoFragment.setArguments(bundle);
            }
        });
        //**********************************************************************************************************************************Show all Favorite movies by clicking button
        showAllFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteMoviesNameArrayList.clear();
                for (int i = 0; i < mm.MOVIES.size(); i++) {
                    FavoriteMoviesNameArrayList.add(mm.MOVIES.get(i).getTitle());
                    favoriteMoviesListView.setAdapter(moviesAdapter);
                    moviesAdapter.notifyDataSetChanged();
                    favoriteSearchBar.setText(null);
                    favoriteSearchNotification.setText("Näytetään kaikki elokuvat:");
                }
            }
        });
    }
}
