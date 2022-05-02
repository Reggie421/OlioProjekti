package com.example.sdidebartesti;


import java.util.ArrayList;

public class AccountManager {
    String username;
    ArrayList <String> favorites;
    private static AccountManager am = new AccountManager();

    private AccountManager(){
        Account a = new Account();
        username = a.getUsername();
        favorites = a.getFavoritemovies();
    }

    public void deleteAccount() {
        MainActivity.getmInstanceActivity().deleteAccount(username);
    }

    private void changePassword() {
        // !TODO JOS KERETÄÄ/JAKSETAA
    }

    public ArrayList<String> getFavorites() {
        return favorites;
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
