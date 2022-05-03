package com.example.sdidebartesti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MovieInfoFragment extends Fragment {
    String movieName;
    TextView movieNameTextView;
    Button fragmentGoBackButton;
    ImageButton addToFavoritesStar;
    TextView castTextView;
    TextView genreTextView;
    TextView yearTextView;
    TextView ageNotAvailableTextView;
    ImageView ageRatingImageView;
    ImageView ratingDescriptionImageView1;
    ImageView ratingDescriptionImageView2;
    ImageView ratingDescriptionImageView3;
    Boolean isClicked = false;

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
        addToFavoritesStar = (ImageButton) view.findViewById(R.id.buttonAddToFavoritesStar);
        castTextView = view.findViewById(R.id.textViewCast);
        genreTextView = view.findViewById(R.id.movieGenreTextView);
        ageNotAvailableTextView = view.findViewById(R.id.textViewAgeNotAvailable);
        ageRatingImageView = view.findViewById(R.id.imageViewAgeRating);
        ratingDescriptionImageView1 = view.findViewById(R.id.imageViewRatingDescription1);
        ratingDescriptionImageView2 = view.findViewById(R.id.imageViewRatingDescription2);
        ratingDescriptionImageView3 = view.findViewById(R.id.imageViewRatingDescription3);
        yearTextView = view.findViewById(R.id.textViewMovieYear);
        MovieManager mm = MovieManager.getInstance();
        AccountManager am = AccountManager.getInstance();
        if(this.getArguments() != null) {
            movieName = this.getArguments().getString("key");
        }
        int movieId = 0;
        for(int i = 0; i < mm.MOVIES.size(); i++){
            if(movieName.equals(mm.MOVIES.get(i).getTitle())){
                movieId = mm.MOVIES.get(i).getId();
            }
        }
        String movieIdString = Integer.valueOf(movieId).toString();
        movieId = Integer.parseInt(movieIdString);

        boolean favoriteChecker = false;
        for(int i = 0; i < am.Accounts.size(); i++){
            if(am.Accounts.get(i).getUsername().equals(MainActivity.getmInstanceActivity().getAccountName())){
                favoriteChecker = am.Accounts.get(i).favoriteSeeker(movieId, 1);
            }
        }
        if (favoriteChecker == true){
            addToFavoritesStar.setBackgroundResource(R.drawable.ic_baseline_star_24);
        }
        else{
            addToFavoritesStar.setBackgroundResource(R.drawable.ic_baseline_star_outline_24);
        }
        movieNameTextView.setText(movieName);
        // *************************************************************************************** Activating "GOBACK" button to reopen the last fragment, this fragment was opened from.
        fragmentGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        String movieGlobalTitle = null;
        String movieYear = null;
        String movieGenre = null;
        String ageRating = null;
        ArrayList<String> ratingDescriptionArrayList = new ArrayList<>();
        ArrayList<CastMember> cast = new ArrayList<>();


        addToFavoritesStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClicked = !isClicked;
                if (isClicked == true) {
                    for(int i = 0; i < mm.MOVIES.size(); i++) {
                        if (movieName.equals(mm.MOVIES.get(i).getTitle())) {
                            int movieId = mm.MOVIES.get(i).getId();
                            for(int j = 0; j < am.Accounts.size(); j++){
                                if(am.Accounts.get(j).getUsername().equals(MainActivity.getmInstanceActivity().getAccountName())){
                                    am.Accounts.get(j).favoriteSeeker(movieId, 2);
                                }
                            }
                        }
                    }
                    view.setBackgroundResource(R.drawable.ic_baseline_star_24);
                } else {
                    for(int i = 0; i < mm.MOVIES.size(); i++) {
                        if (movieName.equals(mm.MOVIES.get(i).getTitle())) {
                            int movieId = mm.MOVIES.get(i).getId();
                            for(int j = 0; j < am.Accounts.size(); j++){
                                if(am.Accounts.get(j).getUsername().equals(MainActivity.getmInstanceActivity().getAccountName())){
                                    am.Accounts.get(j).deleteFromFavorites(movieId);
                                }
                            }

                        }
                    }

                    view.setBackgroundResource(R.drawable.ic_baseline_star_outline_24);
                }
            }
        });

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
        yearTextView.setText(movieYear);
        genreTextView.setText(movieGenre);
// ***************************************************************************************************** Selects proper age rating and contet description images for the movie inspected.
        if(ageRating.equals("Tulossa")){
            ageNotAvailableTextView.setText("Tulossa");
        }
        if(ageRating.equals("(none)")){
            ageNotAvailableTextView.setText("Ei saatavilla");
        }
        else if(ageRating.equals("S")){
            ageRatingImageView.setImageResource(R.drawable.ratings);
        }
        else if(ageRating.equals("7")){
            ageRatingImageView.setImageResource(R.drawable.rating7);
        }
        else if(ageRating.equals("T")){
            ageRatingImageView.setImageResource(R.drawable.ratingt);
        }
        else if(ageRating.equals("12")){
            ageRatingImageView.setImageResource(R.drawable.rating12);
        }
        else if(ageRating.equals("16")){
            ageRatingImageView.setImageResource(R.drawable.rating16);
        }
        else if(ageRating.equals("18")){
            ageRatingImageView.setImageResource(R.drawable.rating18);
        }

        for(int i = 0; i < ratingDescriptionArrayList.size(); i++){
            if (i == 0) {
                if (ratingDescriptionArrayList.get(i).equals("Violence")) {
                    ratingDescriptionImageView1.setImageResource(R.drawable.ratingv);
                }
                else if (ratingDescriptionArrayList.get(i).equals("SexualContent")) {
                    ratingDescriptionImageView1.setImageResource(R.drawable.ratingsex);
                }
                else if (ratingDescriptionArrayList.get(i).equals("Disturbing")) {
                    ratingDescriptionImageView1.setImageResource(R.drawable.ratingspider);
                }
                else if (ratingDescriptionArrayList.get(i).equals("SubstanceAbuse")) {
                    ratingDescriptionImageView1.setImageResource(R.drawable.ratingsubstance);
                }
            }
            if (i == 1){
                if (ratingDescriptionArrayList.get(i).equals("Violence")) {
                    ratingDescriptionImageView2.setImageResource(R.drawable.ratingv);
                }
                else if (ratingDescriptionArrayList.get(i).equals("SexualContent")) {
                    ratingDescriptionImageView2.setImageResource(R.drawable.ratingsex);
                }
                else if (ratingDescriptionArrayList.get(i).equals("Disturbing")) {
                    ratingDescriptionImageView2.setImageResource(R.drawable.ratingspider);
                }
                else if (ratingDescriptionArrayList.get(i).equals("SubstanceAbuse")) {
                    ratingDescriptionImageView2.setImageResource(R.drawable.ratingsubstance);
                }
            }
            if (i == 2){
                if (ratingDescriptionArrayList.get(i).equals("Violence")) {
                    ratingDescriptionImageView3.setImageResource(R.drawable.ratingv);
                }
                else if (ratingDescriptionArrayList.get(i).equals("SexualContent")) {
                    ratingDescriptionImageView3.setImageResource(R.drawable.ratingsex);
                }
                else if (ratingDescriptionArrayList.get(i).equals("Disturbing")) {
                    ratingDescriptionImageView3.setImageResource(R.drawable.ratingspider);
                }
                else if (ratingDescriptionArrayList.get(i).equals("SubstanceAbuse")) {
                    ratingDescriptionImageView3.setImageResource(R.drawable.ratingsubstance);
                }
            }
        }
//********************************************************************************************************* Shows movie cast members in their shared textview
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

}
