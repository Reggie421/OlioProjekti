package com.example.sdidebartesti;

import java.util.ArrayList;

public class Account {
    String username;
    ArrayList <String> favoritemovies;


    public Account() {
        username = MainActivity.getmInstanceActivity().getAccountName();
        favoritemovies = MainActivity.getmInstanceActivity().getFavoriteMovies(username);
    }

    public String getUsername(){ return username;}
    public ArrayList <String> getFavoritemovies(){return favoritemovies;}
}
