package com.example.sdidebartesti;


import java.util.ArrayList;

public class AccountManager {
    private static AccountManager am = new AccountManager();
    Account a;

    private AccountManager(){
        String username = MainActivity.getmInstanceActivity().getAccountName();
        ArrayList<String> favorites = new ArrayList<>();
        favorites = MainActivity.getmInstanceActivity().getFavoriteMovies(username);
        a = new Account(username, favorites);
    }



    public static AccountManager getInstance(){return am;}
}
