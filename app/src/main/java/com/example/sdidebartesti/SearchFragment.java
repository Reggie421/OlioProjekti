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
        showAll = view.findViewById(R.id.buttonShowAllFavorites);
        notification = view.findViewById(R.id.textViewNotificationFavorite);
        notification.setText("Näytetään kaikki elokuvat:");
        ArrayList<String> MoviesArrayList = new ArrayList<String>();
        ArrayAdapter<String> moviesAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, MoviesArrayList);

        for (int i = 0; i < mm.MOVIES.size(); i++) {
            MoviesArrayList.add(mm.MOVIES.get(i).getTitle());
            movieSearchListView.setAdapter(moviesAdapter);
            moviesAdapter.notifyDataSetChanged();
            movieSearch.setText(null);
        }
        movieSearchListView.setAdapter(moviesAdapter);
        // ********************************************************************************************************************** Making list interaction possible on click.
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
            // ******************************************************************************************************************* Making search possible with ENTER key with hiding the software keyboard with ENTER Key
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String searchBarText = movieSearch.getText().toString();
                    MoviesArrayList.clear();
                    for (int i = 0; i < mm.MOVIES.size(); i++) {
                        String comparison = toLowerCase(mm.MOVIES.get(i).getTitle());
                        if (comparison.contains(toLowerCase(searchBarText))) {
                            MoviesArrayList.add(mm.MOVIES.get(i).getTitle());
                            movieSearchListView.setAdapter(moviesAdapter);
                            moviesAdapter.notifyDataSetChanged();
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        }
                        else{
                            if (i == mm.MOVIES.size()-1 && MoviesArrayList.size() == 0){
                                movieSearchListView.setAdapter(null);
                            }
                        }
                    }
                    notification.setText("Näytetään tulokset haulle: '"+ searchBarText +"'");
                    return true;
                }
                return false;
            }
        });
        // ********************************************************************************************************************* Adding functionality to SHOWALL -button.
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoviesArrayList.clear();
                for (int i = 0; i < mm.MOVIES.size(); i++) {
                    MoviesArrayList.add(mm.MOVIES.get(i).getTitle());
                    movieSearchListView.setAdapter(moviesAdapter);
                    moviesAdapter.notifyDataSetChanged();
                    movieSearch.setText(null);
                    notification.setText("Näytetään kaikki elokuvat:");
                }
            }
        });
    }
}
