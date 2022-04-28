package com.example.sdidebartesti;

import android.content.Intent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AccountManager {
    String username;
    ArrayList <String> favorites;
    private static AccountManager am = new AccountManager();

    private AccountManager(){
        username = MainActivity.getmInstanceActivity().getAccountName();
        favorites = MainActivity.getmInstanceActivity().getFavoriteMovies(username);
        Account a = new Account(username,favorites);
    }

    public void deleteAccount() {
        MainActivity.getmInstanceActivity().deleteAccount(username);
    }
    private void changePassword() {

    }
    public void addMovieToFavorites(int movieId){
        boolean booleanChecker = MainActivity.getmInstanceActivity().saveFavoriteMovies(movieId);
        if (booleanChecker == true){
            favorites.add(Integer.toString(movieId));
        }
    }
    public void deleteFromFavorites(int movieId){
        boolean booleanChecker = MainActivity.getmInstanceActivity().deleteFavoriteMovies(movieId);
        if (booleanChecker == true){
            favorites.add(Integer.toString(movieId));
        }

    }
    public static AccountManager getInstance(){return am;}
}
