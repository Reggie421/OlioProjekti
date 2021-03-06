package com.example.sdidebartesti;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PersonSearchFragment extends Fragment {
    ListView personSearchList;
    TextInputEditText searchBar;
    TextView personNotification;

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
        personSearchList = view.findViewById(R.id.movieListView);
        searchBar = view.findViewById(R.id.moviePersonSearchBar);
        personNotification = view.findViewById(R.id.textViewPersonNotification);
        String[] stringMoviesByPersonsArray = new String[MoviesByPersonsArrayList.size()];
        MoviesByPersonsArrayList.toArray(stringMoviesByPersonsArray);
        ArrayAdapter<String> moviesByPersonsAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringMoviesByPersonsArray);
        personSearchList.setAdapter(moviesByPersonsAdapter);
        // ********************************************************************************************************************** Making list interaction possible on click.
        personSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemName = (String) personSearchList.getItemAtPosition(i);
                MovieInfoFragment movieInfoFragment = new MovieInfoFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, movieInfoFragment).addToBackStack(null).commit();
                Bundle bundle = new Bundle();
                bundle.putString("key", itemName);
                movieInfoFragment.setArguments(bundle);
            }
        });
        // ******************************************************************************************************************* Making search possible with ENTER key with hiding the software keyboard with ENTER Key

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
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    personNotification.setText("N??ytet????n tulokset haulle: '"+ searchBarText +"'");
                    return true;
                }
                return false;
            }
        });
    }

}
