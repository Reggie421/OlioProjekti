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
    MaterialButton login;
    MaterialButton signup;
    String usernametext;
    String passwordtext;
    TextView text;
    String salt;
    String hashedPassword;

    // TODO KIRJAUTMISTUNNUS MINKÄ VOI KOPIOIDA: Testi - Aa#1aaaaaaaa
    //TODO KORJATAAN ONGELMA KUN KÄYTTÄJÄ KÄYNNISTÄÄ ENSIMMÄISEN KERRAN SOVELLUKSEN (LUETTAVAA TIEDOSTA EI LÖYTYNYT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (MaterialButton) findViewById(R.id.login);
        signup = (MaterialButton) findViewById(R.id.signup);
        text = (TextView) findViewById(R.id.Salasanatextview);
        System.out.println("penis");
        salt = getSalt();

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
                    login.setEnabled(false);
                    signup.setEnabled(false);
                }
            }
        });

        user.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    final InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    final InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernametext = user.getText().toString();
                passwordtext = password.getText().toString();
                if (usernametext.isEmpty()) {
                    System.out.println("Et antanut käyttäjätunnusta.");
                } else {
                    hashedPassword = get_SHA_512_SecurePassword(passwordtext, salt);
                    int bump = SearchAccountList(usernametext, hashedPassword, 1);
                    if (bump == 0) {
                        user.getText().clear();
                        password.getText().clear();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", usernametext);
                        startActivity(intent);
                    } else if (bump == 1) {
                        password.getText().clear();
                        text.setText("Väärä salasana");
                    } else if (bump == 2) {
                        user.getText().clear();
                        password.getText().clear();
                        text.setText("Käyttäjää ei olemassa.");
                    }
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
                if (usernametext.isEmpty() || passwordtext.isEmpty()) {
                    System.out.println("jompikumpi on tyhjä");
                } else {
                    hashedPassword = get_SHA_512_SecurePassword(passwordtext, salt);
                    boolean bump = createAccount(usernametext, hashedPassword);
                    if (bump == true) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", usernametext);
                        startActivity(intent);
                    } else {
                        text.setText("Käyttäjätunnus varattu");
                        login.setEnabled(false);
                        signup.setEnabled(false);
                    }
                }
            }
        });
    }


    public int SearchAccountList(String username, String password, int number){
        try {
            FileInputStream fileInputStream = openFileInput("accounts.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            if (number == 2) {
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
                    return 1;
                } else {
                    System.out.println("Käyttäjätunnus varattu");
                    return 2;
                }
            } else {
                int credentialsCorrect = 0;
                int usernameCorrect = 0;
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append((lines + "\n"));
                    String[] data = lines.split(";");
                    if (data[0].equals(username) && data[1].equals(password)) {
                        credentialsCorrect++;
                    } else if (data[0].equals(username) && !data[1].equals(password)) {
                        usernameCorrect++;
                    } else {
                    }
                }
                if (credentialsCorrect == 1) {
                    System.out.println("Käyttäjätiedot oikein ja olemassa");
                    return 0;
                } else if (usernameCorrect == 1){
                    System.out.println("Salasana väärin");
                    return 1;
                } else {
                    System.out.println("Tämän nimistä käyttäjää ei olemassa");
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

    public boolean createAccount(String username, String password){
        String row = "";
        int value = SearchAccountList(username, password, 2);
        if (value == 1) {
            row = username + ";" + password + ";null\n";
            try {
                FileOutputStream fileOutputStream = openFileOutput("accounts.csv",MODE_PRIVATE);
                fileOutputStream.write(row.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            System.out.println("username varattu");
            return false;
        }
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private static String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt.toString();
    }

    private static String get_SHA_512_SecurePassword(String unsecurePassword, String salt){
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