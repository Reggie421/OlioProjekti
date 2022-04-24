package com.example.sdidebartesti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MovieInfoFragment extends Fragment {
    String movieName;
    TextView movieNameTextView;
    Button fragmentGoBackButton;
    TextView directorsTextView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movieinfo, container, false);
    }

    @Override
    // ****************************************************************************************** Receiving movie name from the fragment, this fragment was opened from.
    //******************************************************************************************* Then fetching rest movie information using this name.
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieNameTextView = view.findViewById(R.id.movieNameTextView);
        fragmentGoBackButton = view.findViewById(R.id.buttonCloseFragment);
        directorsTextView = view.findViewById(R.id.textViewCast);
        if(this.getArguments() != null) {
            movieName = this.getArguments().getString("key");
        }
        System.out.println(movieName);
        movieNameTextView.setText(movieName);
        // *************************************************************************************** Activating "GOBACK" button to reopen the last fragment, this fragment was opened from.
        fragmentGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        MovieManager mm = MovieManager.getInstance();
        String movieGlobalTitle = null;
        String movieYear = null;
        String movieGenre = null;
        String ageRating = null;
        ArrayList<CastMember> cast = new ArrayList<>();

        for(int i = 0; i < mm.MOVIES.size(); i++){
            if(movieName.equals(mm.MOVIES.get(i).getTitle())){
                movieGlobalTitle = mm.MOVIES.get(i).getGlobalTitle();
                movieYear = mm.MOVIES.get(i).getYear();
                movieGenre = mm.MOVIES.get(i).getMovieGenre();
                ageRating = mm.MOVIES.get(i).getAgeRating();
                cast = mm.MOVIES.get(i).CastList;
            }
        }
        int index = 0;
        for(int i = 0; i < cast.size(); i++){
            if(cast.get(i).getRole() == "director") {
                index += 1;
            }
        }
        if (index == 0){
            directorsTextView.setText(null);
        }
        else if(index == 1){
            directorsTextView.setText("Ohjaaja:");
        }
        else {
            directorsTextView.setText("Ohjaajat:");
        }
    }
} // TODO: hae dataa elokuvan nimellä
// TODO: iMDB arvosana
