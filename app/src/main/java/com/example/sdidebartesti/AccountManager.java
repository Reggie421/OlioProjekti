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
        System.out.println("Testi1");

        username = MainActivity.getmInstanceActivity().getAccountName();
        System.out.println("testi2");
        favorites = MainActivity.getmInstanceActivity().getFavoriteMovies(username);
        System.out.println("testi3");
        Account a = new Account(username,favorites);
    }

    public void deleteAccount() {
        MainActivity.getmInstanceActivity().deleteAccount(username);
    }
    private void changePassword() {

    }
    public static AccountManager getInstance(){return am;}
}
