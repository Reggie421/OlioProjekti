package com.example.sdidebartesti;

import java.util.ArrayList;

public class Account {
    String username;
    ArrayList <String> favoritemovies;

    public Account(String newUsername, ArrayList<String> newfavoriteMovies) {
        username = newUsername;
        favoritemovies = newfavoriteMovies;
    }
    public String getUsername(){ return username;}

    public ArrayList <String> getFavoritemovies(){
        favoritemovies = MainActivity.getmInstanceActivity().getFavoriteMovies(username);
        return favoritemovies;}

    public void deleteFromFavorites(int removeID){
        MainActivity.getmInstanceActivity().deleteFavoriteMovies(removeID);
        for(int i = 0; i < favoritemovies.size(); i++){
            if(favoritemovies.get(i).equals(Integer.valueOf(removeID).toString())){
                favoritemovies.remove(i);
            }
        }
    }

    public boolean favoriteSeeker(int movieId, int pathInt){
        boolean booleanChecker = MainActivity.getmInstanceActivity().saveFavoriteMovies(movieId,pathInt);
        if (pathInt == 1) {
            if (booleanChecker == true){
                return true;
            }
            return false;
        }
        else{
            if (booleanChecker == true){
                favoritemovies.add(Integer.toString(movieId));
            }
        }
        return false;
    }
}
