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

public class FavoriteFragment extends Fragment {

    ListView favoriteMoviesListView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteMoviesListView = view.findViewById(R.id.favoriteListView);
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
    }
}
