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
import java.lang.reflect.Array;
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
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
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
    public void writeFile(int id, String title, String globalTitle, String yearString, ArrayList<CastMember> castMemberArrayList, String genres, String rating, ArrayList <String> ratingDescriptions) throws IOException {
        String actors = "";
        String directors = "";
        String row = "";
        String ratingDescriptionsString = "";
        for (int i = 0 ; i < castMemberArrayList.size() ; i++){
            if(castMemberArrayList.get(i).getRole() == "director"){
                directors += castMemberArrayList.get(i).getFirstName() + "/" + castMemberArrayList.get(i).getLastName()+ ",";
            }
            else {
                actors += castMemberArrayList.get(i).getFirstName() + "/" + castMemberArrayList.get(i).getLastName()+ ",";

            }
        }
        if (actors.equals("")){
            actors = "null";
        }
        if (directors.equals("")){
            directors = "null";
        }

        for (int i = 0 ; i < ratingDescriptions.size() ; i++){
                ratingDescriptionsString += ratingDescriptions.get(i) + "/";
        }
        if (ratingDescriptionsString.equals("")){
            ratingDescriptionsString = "null";
        }
        row = id+";"+yearString+";"+title+";"+globalTitle+";"+genres+";"+rating+";"+ratingDescriptionsString+";"+actors+";"+directors +"\n";

        try {
            FileOutputStream fileOutputStream = openFileOutput("Movies1.csv",MODE_APPEND);
            fileOutputStream.write(row.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Movie> readFile() {
        ArrayList <Movie> temporaryMovieArrayList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput("Movies1.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            MovieManager mm = MovieManager.getInstance(); //TODO Poista?
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                ArrayList <CastMember> castMemberArrayList = new ArrayList<CastMember>();
                ArrayList<String> ratingDescriptionsArrayList = new ArrayList<>();
                stringBuffer.append((lines + "\n"));
                String[] data = lines.split(";");
                int id = Integer.parseInt(data[0]);
                String yearString = data[1];
                String title = data[2];
                String globalTitle = data[3];
                String genres = data[4];
                String ageRating = data[5];
                if(!data[6].equals("null")) {
                    String[] ratingsDescriptions = data[6].split("/");
                    for(int i = 0; i < ratingsDescriptions.length; i++){
                        String ratingsDescription = ratingsDescriptions[i];
                        ratingDescriptionsArrayList.add(ratingsDescription);
                    }
                }
                if (!data[7].equals("null")){
                    String[] castData = data[7].split(",");
                    for (int i = 0 ; i < castData.length ; i++){
                        String[] castNameData = castData[i].split("/");
                        String actorFirstName = castNameData[0];
                        String actorLastName = castNameData[1];
                        CastMember actor = new CastMember(actorFirstName, actorLastName,"actor");
                        castMemberArrayList.add(actor);
                    }
                }
                if (!data[8].equals("null")){
                    String[] castData = data[8].split(",");
                    for (int i = 0 ; i < castData.length ; i++) {
                        String[] castNameData = castData[i].split("/");
                        String directorFirstName = castNameData[0];
                        String directorLastName = castNameData[1];
                        CastMember director = new CastMember(directorFirstName, directorLastName, "director");
                        castMemberArrayList.add(director);
                    }
                }

                Movie m = new Movie(id, title, globalTitle, yearString,castMemberArrayList, genres, ageRating, ratingDescriptionsArrayList);
                temporaryMovieArrayList.add(m);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return(temporaryMovieArrayList);
    }

}