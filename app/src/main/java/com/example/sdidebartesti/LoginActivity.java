package com.example.sdidebartesti;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText user;
    EditText password;
    MaterialButton loginButton;
    MaterialButton signupButton;
    String usernametext;
    String passwordtext;
    TextView passwordRequirement;
    TextView errorMessage;
    String salt;
    String hashedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (MaterialButton) findViewById(R.id.loginButton);
        signupButton = (MaterialButton) findViewById(R.id.signupButton);
        passwordRequirement = (TextView) findViewById(R.id.passwordRequirement);
        errorMessage = (TextView) findViewById(R.id.ErrorMessages);
        loginButton.setEnabled(false);
        signupButton.setEnabled(false);
        password.addTextChangedListener(new TextWatcher() {//listener for password textview, so when password meets requirements, you can login/signup
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isValidPassword(editable.toString().trim())) {//login/signup buttons come available
                    loginButton.setEnabled(true);
                    signupButton.setEnabled(true);
                    passwordRequirement.setVisibility(View.INVISIBLE);
                } else {//login/signup buttons unavailable
                    loginButton.setEnabled(false);
                    signupButton.setEnabled(false);
                    passwordRequirement.setVisibility(View.VISIBLE);
                }
            }
        });

        user.setOnKeyListener(new View.OnKeyListener() { //hides integrated keyboard when pressed enter while on edittext
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    final InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return false;
                }
                return false;
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() { //hides integrated keyboard when pressed enter while on edittext
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    final InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return false;
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() { //listener for what happens when loginButton is pressed
            @Override
            public void onClick(View v) {
                usernametext = user.getText().toString(); //gets the text from username field
                passwordtext = password.getText().toString(); //gets the text from password field
                if (usernametext.isEmpty()) {
                    errorMessage.setText("Et antanut käyttäjätunnusta"); //if username field is empty, manual error message and nothing happens
                } else {
                    int bump = SearchAccountList(usernametext, passwordtext, 1); //calls method for searching the accountlist for any account of the same name
                    if (bump == 0) {//if account exists and password is correct
                        user.getText().clear();
                        password.getText().clear();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", usernametext);
                        startActivity(intent);
                    } else if (bump == 1) {//if account exists but password is incorrect, clear password input and show manual error message
                        password.getText().clear();
                        errorMessage.setText("Väärä salasana");
                    } else if (bump == 2) {//if account doesn't exist, clear input fields and show manual error message
                        user.getText().clear();
                        password.getText().clear();
                        errorMessage.setText("Käyttäjää ei olemassa.");
                    }
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {//listener for what happens when signupButton is pressed
            @Override
            public void onClick(View view) {
                usernametext = user.getText().toString(); //gets the text from username field
                passwordtext = password.getText().toString(); //gets the text from password field
                user.getText().clear();
                password.getText().clear();
                salt = getSalt(); //calling salt-method
                if (usernametext.isEmpty() || passwordtext.isEmpty()) {//if either field is empty, manual error message and user can retry
                    errorMessage.setText("Et antanut vaadittavia tietoja.");
                } else {
                    hashedPassword = get_SHA_512_SecurePassword(passwordtext, salt);//calls method for converting password string to hashed password
                    hashedPassword = hashedPassword + "/" + salt;
                    boolean bump = createAccount(usernametext, hashedPassword);//method for creating new account
                    if (bump == true) {//if there is no account of that name yet, creating succeeds and you get in
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", usernametext);
                        startActivity(intent);
                    } else {// an account with the same name already exists, manual error message
                        errorMessage.setText("Käyttäjätunnus varattu.");
                        loginButton.setEnabled(false);
                        signupButton.setEnabled(false);
                    }

                }
            }
        });
    }


    public int SearchAccountList(String username, String password, int number){//the method for searching for accounts with the same name
        try {
            FileInputStream fileInputStream = openFileInput("accounts.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            if (number == 2) {//if method is called while Creating new account
                int i = 0;
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append((lines + "\n"));
                    String[] data = lines.split(";");
                    if (data[0].equals(username)) {//if any line contains the same username the user is trying to create
                        i++;
                    }
                }
                if (i == 0) {//Username isn't in use yet
                    return 1;
                } else {//Username is already taken by another account
                    return 2;
                }
            } else {//if method is called while trying to log in
                int credentialsCorrect = 0;
                int usernameCorrect = 0;
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append((lines + "\n"));
                    String[] data = lines.split(";");
                    if (data[0].equals(username)) {//if user inputted username has an account, search the file for the account and verify the password
                        String[] passwordAndSalt = data[1].split("/");
                        String hashedPassword = passwordAndSalt[0];
                        String salt = passwordAndSalt[1];
                        if (hashedPassword.equals(get_SHA_512_SecurePassword(password, salt))) {//if user inputted password is equal with the password of the account with the same name
                            credentialsCorrect++;
                        } else {//user inputted password isn't equal with the password of the account
                            usernameCorrect++;
                        }
                    }
                }
                if (credentialsCorrect == 1) {//user input credentials are correct so log in will be successful
                    return 0;
                } else if (usernameCorrect == 1){// user input password is wrong so log in won't be successful but user will get to try again
                    return 1;
                } else {//user input username doesn't exist so log in won't be successful
                    return 2;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public boolean createAccount(String username, String password){//method for creating a new account
        String row = "";
        int value = SearchAccountList(username, password, 2);//calls method for finding out if username is taken
        if (value == 1) {//username isn't taken, will add new account to storage
            row = username + ";" + password + ";null\n";
            try {
                FileOutputStream fileOutputStream = openFileOutput("accounts.csv",MODE_APPEND);
                fileOutputStream.write(row.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {//username is already taken
            return false;
        }
    }

    public boolean isValidPassword(final String password) {//method for checking if password is valid
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$";//contains at least 1 number, upper- and lowercase letter, special character, at least 12 characters, no whitespace
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private static String getSalt() {//method for creating random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt.toString();
    }

    private static String get_SHA_512_SecurePassword(String unsecurePassword, String salt){//method for creating the secure/hashed password
        String securePassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(unsecurePassword.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            securePassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return securePassword;
    }
}