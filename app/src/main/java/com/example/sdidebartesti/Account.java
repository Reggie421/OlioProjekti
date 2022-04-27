package com.example.sdidebartesti;

import java.util.ArrayList;

public class Account {
    String username;
    ArrayList <String> favoritemovies;


    public Account(String user, ArrayList <String> favmovies) {
        username = user;
        favoritemovies = favmovies;
    }

    public String getUsername(){return username;}
    public ArrayList <String> getFavoritemovies(){return favoritemovies;}
}
