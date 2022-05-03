package com.example.sdidebartesti;

import java.util.ArrayList;

public class Account {
    String username;
    ArrayList <String> favoritemovies;

    public Account(String Username, ArrayList<String> Favoritemovies) {
        username = Username;
        favoritemovies = Favoritemovies;
    }

    public String getUsername(){ return username;}
    public ArrayList <String> getFavoritemovies(){return favoritemovies;}

    public void deleteAccount() {
        MainActivity.getmInstanceActivity().deleteAccount(username);
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

    public void deleteFromFavorites(int movieId){
        MainActivity.getmInstanceActivity().deleteFavoriteMovies(movieId);
        System.out.println("poistettuxd3");
        String movieIdString = Integer.valueOf(movieId).toString();
        for(int i = 0; i< favoritemovies.size();i++){
            if(favoritemovies.get(i).equals(movieIdString) ){
                favoritemovies.remove(i);
                System.out.println("poistettuxdFinal "+ movieIdString);
            }
        }
    }
}
