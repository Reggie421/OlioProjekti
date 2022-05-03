package com.example.sdidebartesti;


import java.util.ArrayList;

public class AccountManager {
    private static AccountManager am = new AccountManager();
    ArrayList<Account> Accounts;

    public void addAccount(){
        Accounts = new ArrayList<>();
        String username = MainActivity.getmInstanceActivity().getAccountName();
        ArrayList<String> favorites = new ArrayList<>();
        favorites = MainActivity.getmInstanceActivity().getFavoriteMovies(username);
        Account a = new Account(username, favorites);
        Accounts.add(a);
    }

    public ArrayList<String> getFavoriteMoviesByUsername(String username){
        ArrayList<String> returnArrayList = null;
        for(int i = 0; i< am.Accounts.size(); i++){
            if(am.Accounts.get(i).getUsername().equals(username)){
               returnArrayList = am.Accounts.get(i).getFavoritemovies();
            }
        }
        return (returnArrayList);
    }

    public void deleteAccount(String username) {
        MainActivity.getmInstanceActivity().deleteAccount(username);
        for(int i = 0; i< am.Accounts.size(); i++){
            if(am.Accounts.get(i).getUsername().equals(username)){
                Accounts.remove(i);
            }
        }
    }


    public static AccountManager getInstance(){return am;}
}
