package com.example.sdidebartesti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText user;
    EditText password;
    MaterialButton login;
    MaterialButton signup;
    String usernametext;
    String passwordtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (MaterialButton) findViewById(R.id.login);
        signup = (MaterialButton) findViewById(R.id.signup);

        login.setEnabled(false);
        signup.setEnabled(false);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isValidPassword(editable.toString().trim())) {
                    login.setEnabled(true);
                    signup.setEnabled(true);
                } else {
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernametext = user.getText().toString();
                passwordtext = password.getText().toString();
                user.getText().clear();
                password.getText().clear();
                if (usernametext.isEmpty() || passwordtext.isEmpty()) {
                    System.out.println("jompikumpi on tyhjä");
                } else {
                    System.out.println(usernametext);
                    System.out.println(passwordtext);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", usernametext);
                    startActivity(intent);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernametext = user.getText().toString();
                passwordtext = password.getText().toString();
                user.getText().clear();
                password.getText().clear();
                createAccount(usernametext, passwordtext);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean SearchAccountList(String username, String password, int number){
        if (number == 1) {
            return true;
        } else {
            try {
                FileInputStream fileInputStream = openFileInput("accounts1.csv");
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String lines;
                int i = 0;
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append((lines + "\n"));
                    String[] data = lines.split(";");
                    if (data[0].equals(username)) {
                        i++;
                    }
                }
                if (i == 0) {
                    System.out.println("ei samaa usernamea");
                    return false;
                } else {
                    System.out.println("sama username löytyi");
                    return true;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public void createAccount(String username, String password){
        int number = 2;
        boolean value;
        String row = "";
        value = SearchAccountList(username, password, number);
        if (value == false) {
            row = username + ";" + password + ";\n";
            try {
                FileOutputStream fileOutputStream = openFileOutput("accounts1.csv",MODE_APPEND);
                fileOutputStream.write(row.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("username varattu");
        }

    }
}