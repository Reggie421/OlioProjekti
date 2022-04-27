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

import org.w3c.dom.Text;

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
    TextView text;
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
                user.getText().clear();
                password.getText().clear();
                if (usernametext.isEmpty() || passwordtext.isEmpty()) {
                    System.out.println("jompikumpi on tyhjä");
                } else {
                    boolean bump = SearchAccountList(usernametext, passwordtext, 1);
                    if (bump == true) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", usernametext);
                        startActivity(intent);
                    } else {
                        text.setText("Jotain tais mennä pieleen xd");
                        login.setEnabled(false);
                        signup.setEnabled(false);
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
                    boolean bump = createAccount(usernametext, passwordtext);
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

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean SearchAccountList(String username, String password, int number){
        try {
            FileInputStream fileInputStream = openFileInput("accounts1.csv");
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
                    return false;
                } else {
                    System.out.println("sama username löytyi");
                    return true;
                }
            } else {
                int i = 0;
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append((lines + "\n"));
                    String[] data = lines.split(";");
                    if (data[0].equals(username) && data[1].equals(password)) {
                        i++;
                    }
                }
                if (i == 0) {
                    System.out.println("Jompikumpi feilas");
                    return false;
                } else {
                    System.out.println("Pääset sisään");
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAccount(String username, String password){
        boolean value;
        String row = "";
        value = SearchAccountList(username, password, 2);
        if (value == false) {
            row = username + ";" + password + ";null\n";
            try {
                FileOutputStream fileOutputStream = openFileOutput("accounts1.csv",MODE_PRIVATE);
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
}