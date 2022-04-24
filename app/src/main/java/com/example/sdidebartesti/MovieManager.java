package com.example.sdidebartesti;

import android.app.Activity;
import android.os.StrictMode;

import androidx.fragment.app.FragmentManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MovieManager {
    private static MovieManager mm = new MovieManager();
    ArrayList<Movie> MOVIES;

    private MovieManager(){
        MOVIES = new ArrayList<Movie>();
        String url_FINKINO = "https://www.finnkino.fi/xml/Events/";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        MOVIES = MainActivity.getmInstanceActivity().readFile();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url_FINKINO);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: "+doc.getDocumentElement().getNodeName());
            NodeList nList1 = doc.getElementsByTagName("Event");
            int x = 0;
            for (int i = 0; i < nList1.getLength(); i++ ){
                Node node = nList1.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    String globalTitle = element.getElementsByTagName("OriginalTitle").item(0).getTextContent();
                    String yearString = ((element.getElementsByTagName("dtLocalRelease").item(0).getTextContent()).toString()).substring(0,4);
                    int yearInt = Integer.parseInt(yearString);
                    System.out.println("-----");
                    System.out.println("Elokuva alla "+title);
                    System.out.println("---");
                    // NÄYTTELIJÄ LISTA LUONTI ja lisätään ohjaaja ensimmäiseksi listaan
                    String director = element.getElementsByTagName("Directors").item(0).getTextContent();
                    ArrayList <CastMember> castMemberArrayList = new ArrayList<CastMember>();
                    String[] directorLinesArr = director.split("\n");
                    for (int j = 2; j < directorLinesArr.length; j++) {
                        String directorFirstName = directorLinesArr[j].substring(8);
                        String directorLastName = directorLinesArr[j+1].substring(8);
                        CastMember dir = new CastMember(directorFirstName, directorLastName, "director");
                        castMemberArrayList.add(dir);
                        j += 3;
                    }
                    String cast = element.getElementsByTagName("Cast").item(0).getTextContent();
                    String[] linesArr = cast.split("\n");
                    for (int j = 2 ; j < linesArr.length ; j++ ){
                        String firstName = linesArr[j].substring(8);
                        String lastName = linesArr[j+1].substring(8);
                        CastMember actor = new CastMember(firstName, lastName, "actor");
                        System.out.println(firstName+" "+lastName);
                        castMemberArrayList.add(actor);
                        j += 3;
                    }
                    for(int indeksi = 0; indeksi < castMemberArrayList.size(); indeksi++){
                        System.out.println(castMemberArrayList.get(indeksi).firstName + " " + castMemberArrayList.get(indeksi).lastName);

                    }
                    //ELOKUVAOLION LUONTI
                    // IF MOVIES ARRAYLIST DOESNT INCLUDE ANY MOVIES WITH 'id', CREATING NEW OBJECT FOR THAT MOVIE == NEW MOVIE FROM INTERNET
                    int counter = 0;
                    for (i = 0 ; i < MOVIES.size() ; i++){
                        if (id == MOVIES.get(i).getId()) {
                            break;
                        }
                        else{
                            counter++;
                        }
                    }
                    if (counter == MOVIES.size()) {
                        /*float rating = searchRating(originalTitle,yearInt);*/ //<-elokuvainfo fragmentissa

                        Movie m = new Movie(id, title, globalTitle, yearString,castMemberArrayList);
                        MOVIES.add(m);
                        MainActivity.getmInstanceActivity().writeFile(id, title, globalTitle, yearString,castMemberArrayList);
                    }

                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private float searchRating(String name, int yearInt){
        float rating = 0;
        String response = null;
        String id_IMDB = null;
        String url_IMDB_searchID = "https://imdb-api.com/en/API/SearchMovie/k_lcn8k0fb/"+name+" "+yearInt;
        try{
            URL url = new URL(url_IMDB_searchID);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in =new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = br.readLine())!= null){
                sb.append(line).append("\n");
            }
            response = sb.toString();
            if (response.substring(response.indexOf("[")+17,response.indexOf("[")+18).equals("'")){
                id_IMDB = response.substring(response.indexOf("[")+8,response.indexOf("[")+17);
            }
            else{
                id_IMDB = response.substring(response.indexOf("[")+8,response.indexOf("[")+18);
            }
            in.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url_IMDB_searchRating = "https://imdb-api.com/en/API/Ratings/k_lcn8k0fb/"+id_IMDB;
        try{
            URL url = new URL(url_IMDB_searchRating);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in =new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = br.readLine())!= null){
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();
            String character = response.substring(response.indexOf("metacritic")-6,response.indexOf("metacritic")-5);
            if (character.equals("0") || character.equals("1") || character.equals("2") || character.equals("3") || character.equals("4") || character.equals("5") || character.equals("6") || character.equals("7") || character.equals("8") || character.equals("9")){
                rating = Float.valueOf(response.substring(response.indexOf("metacritic")-6,response.indexOf("metacritic")-3));

            }
            else{
                rating = 0;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rating;
    }

    public static MovieManager getInstance(){
        return mm;
    }
}
