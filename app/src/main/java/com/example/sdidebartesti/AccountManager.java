package com.example.sdidebartesti;

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

    private void deleteAccount() {

    }
    private void changePassword() {

    }
    public static AccountManager getInstance(){return am;}
}
