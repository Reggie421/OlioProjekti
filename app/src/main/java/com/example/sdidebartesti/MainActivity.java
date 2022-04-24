package com.example.sdidebartesti;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static WeakReference<MainActivity> weakActivity;
    // etc..
        public static MainActivity getmInstanceActivity() {
            return weakActivity.get();
        }

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weakActivity = new WeakReference<>(MainActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Creates animation for drawer icon
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer = (NavigationView) findViewById(R.id.navigationView);
        setupDrawerContent(nvDrawer);
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, homeFragment).commit();
        MovieManager mm = MovieManager.getInstance();
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }
//This method runs the wanted fragments from the selection of the side bar.
    public void selectDrawerItem(MenuItem item){
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()){
            case R.id.home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.search:
                fragmentClass = SearchFragment.class;
                break;
            case R.id.personsearch:
                fragmentClass = PersonSearchFragment.class;
                break;
            case R.id.favorites:
                fragmentClass = FavoriteFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }
        try{
            fragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception E) {
            E.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void writeFile(int id, String title, String globalTitle, String yearString, ArrayList<CastMember> castMemberArrayList) throws IOException {
        String actors = "";
        String directors = "";
        String row = "";
        for (int i = 0 ; i < castMemberArrayList.size() ; i++){
            actors += castMemberArrayList.get(i).getFirstName() + " " + castMemberArrayList.get(i).getLastName()+ ",";
        }
        if (castMemberArrayList.size() == 0){
            actors = "null";
        }
        row = id+";"+yearString+";"+title+";"+globalTitle+";"+actors+";"+directors +"\n";

        try {
            FileOutputStream fileOutputStream = openFileOutput("TestFile1.csv",MODE_APPEND);
            fileOutputStream.write(row.getBytes());
            fileOutputStream.close();
            System.out.println("tallennettu tekstitiedostoon----------------------");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readFile() {
        ArrayList <CastMember> castMemberArrayList = new ArrayList<CastMember>();
        try {
            FileInputStream fileInputStream = openFileInput("TestFile1.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            MovieManager mm = MovieManager.getInstance();
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                System.out.println(lines);
                stringBuffer.append((lines + "\n"));
                String[] data = lines.split(";");
                int id = Integer.parseInt(data[0]);
                System.out.println(id);
                String yearString = data[1];
                System.out.println(yearString);
                String title = data[2];
                System.out.println(title);
                String globalTitle = data[3];
                System.out.println(globalTitle);
                System.out.println("testi");
                if (data[4] != "null"){
                    System.out.println("testi2");
                    String[] castData = data[4].split(",");
                    for (int i = 0 ; i < castData.length ; i++){
                        String[] castNameData = castData[i].split(" ");
                        String directorFirstName = castNameData[0];
                        String directorLastName = castNameData[1];
                        CastMember dir = new CastMember(directorFirstName, directorLastName);
                        castMemberArrayList.add(dir);
                    }
                }
                System.out.println("testi3");
                Movie m = new Movie(id, title, globalTitle, yearString,castMemberArrayList);
                System.out.println("testi4");
                mm.MOVIES.add(m);
                System.out.println("testi5");
            }
            System.out.println(stringBuffer.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}