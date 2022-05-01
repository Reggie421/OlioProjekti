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
    public boolean favoriteSeeker(int movieId,int pathInt){
        boolean booleanChecker = MainActivity.getmInstanceActivity().saveFavoriteMovies(movieId,pathInt);
        if (pathInt == 1) {
            if (booleanChecker == true){
                return true;
            }
            return false;
        }
        else{
            if (booleanChecker == true){
                favorites.add(Integer.toString(movieId));
            }
        }
        return false;
    }
    public void deleteFromFavorites(int movieId){
        MainActivity.getmInstanceActivity().deleteFavoriteMovies(movieId);
        System.out.println("poistettuxd3");
        String movieIdString = Integer.valueOf(movieId).toString();
        for(int i = 0; i< favorites.size();i++){
            if(favorites.get(i).equals(movieIdString) ){
                favorites.remove(i);
                System.out.println("poistettuxdFinal");
            }
        }
    }
    public static AccountManager getInstance(){return am;}
}
