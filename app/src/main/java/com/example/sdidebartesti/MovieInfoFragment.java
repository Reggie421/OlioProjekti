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
    TextView castTextView;
    TextView genreTextView;
    TextView ageRatingTextView;
    TextView ratingDescriptionTextView;

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
        castTextView = view.findViewById(R.id.textViewCast);
        genreTextView = view.findViewById(R.id.movieGenreTextView);
        ageRatingTextView = view.findViewById(R.id.textViewAgeRating);
        ratingDescriptionTextView = view.findViewById(R.id.textViewRatingDescription);

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
        ArrayList<String> ratingDescriptionArrayList = new ArrayList<>();
        ArrayList<CastMember> cast = new ArrayList<>();

        for(int i = 0; i < mm.MOVIES.size(); i++){
            if(movieName.equals(mm.MOVIES.get(i).getTitle())){
                movieGlobalTitle = mm.MOVIES.get(i).getGlobalTitle();
                movieYear = mm.MOVIES.get(i).getYear();
                movieGenre = mm.MOVIES.get(i).getMovieGenre();
                ageRating = mm.MOVIES.get(i).getAgeRating();
                cast = mm.MOVIES.get(i).getCastList();
                ratingDescriptionArrayList = mm.MOVIES.get(i).getRatingDescription();
            }
        }
        genreTextView.setText(movieGenre);
        ageRatingTextView.setText(ageRating);
        if(ageRating.equals("(none)")){
            ageRatingTextView.setText("Ei saatavilla");
        }
        if(ratingDescriptionArrayList.size() == 0){
            ratingDescriptionTextView.setText(null);
        }
        for(int i = 0; i < ratingDescriptionArrayList.size(); i++){
            ratingDescriptionTextView.append(ratingDescriptionArrayList.get(i) + "\n");
        }
        System.out.println("ELOKUVA ALKKA ******" + movieName);
        for(int i = 0; i < ratingDescriptionArrayList.size(); i++){
            System.out.println("*************" + ratingDescriptionArrayList.get(i));
        }
        int directorIndex = 0;
        for(int i = 0; i < cast.size(); i++){
            if(cast.get(i).getRole() == "director") {
                directorIndex += 1;
            }
        }
        if (directorIndex == 0){
            castTextView.setText("Ei ohjaajia");
        }
        else if(directorIndex == 1){
           castTextView.setText("Ohjaaja: ");
        }
        else {
            castTextView.setText("Ohjaajat: ");
        }
        castTextView.append("\n");
        if (directorIndex != 0) {
            for (int i = 0; i < cast.size(); i++) {
                if (cast.get(i).getRole() == "director") {
                    castTextView.append(cast.get(i).getFirstName() + " " + cast.get(i).getLastName() + "\n");
                }
            }
        }
        int actorIndex = 0;
        for(int i = 0; i < cast.size(); i++){
            if(cast.get(i).getRole() == "actor") {
                actorIndex += 1;
            }
        }
        castTextView.append("\n");
        if (actorIndex == 0){
            castTextView.append("Ei näyttelijöitä");
        }
        else if(actorIndex == 1){
            castTextView.append("Näyttelijä: ");
        }
        else {
            castTextView.append("Näyttelijät: ");
        }
        castTextView.append("\n");
        if (actorIndex != 0) {
            for (int i = 0; i < cast.size(); i++) {
                if (cast.get(i).getRole() == "actor") {
                    castTextView.append(cast.get(i).getFirstName() + " " + cast.get(i).getLastName() + "\n");
                }
            }
        }
    }
} // TODO: hae dataa elokuvan nimellä
// TODO: iMDB arvosana
