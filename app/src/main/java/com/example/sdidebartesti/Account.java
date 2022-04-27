package com.example.sdidebartesti;

import java.util.ArrayList;

public class Account {
    String username;
    String password;
    String lastFragment;
    ArrayList <String> favoritemovies;


    public Account(String user, String pw, String lF, ArrayList <String> favmovies) {
        username = user;
        password = pw;
        lastFragment = lF;
        favoritemovies = favmovies;
    }

    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getLastFragment(){return lastFragment;}
    public ArrayList <String> getFavoritemovies(){return favoritemovies;}
}
