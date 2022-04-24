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
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

public class MovieInfoFragment extends Fragment {
    String movieName;
    TextView movieNameTextView;
    Button fragmentGoBackButton;

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
        for(int i = 0; i < mm.MOVIES.size(); i++){
            if(movieName.equals(mm.MOVIES.get(i).getTitle())){
                String movieGlobalTitle = mm.MOVIES.get(i).getGlobalTitle();
                String movieYear = mm.MOVIES.get(i).getYear();
                String movieGenre = mm.MOVIES.get(i).getMovieGenre();
            }
        }

    }
} // TODO: hae dataa elokuvan nimellä
// TODO: iMDB arvosana
