package com.example.sdidebartesti;

import android.os.StrictMode;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
                    String genres = element.getElementsByTagName("Genres").item(0).getTextContent();
                    String yearString = ((element.getElementsByTagName("dtLocalRelease").item(0).getTextContent()).toString()).substring(0,4);
                    String ageRating = element.getElementsByTagName("Rating").item(0).getTextContent();
                    String ratingDescript = element.getElementsByTagName("ContentDescriptors").item(0).getTextContent();
                    ArrayList <String> ratingDescriptions = new ArrayList<>();
                    String[] ratingDescriptionLinesArr = ratingDescript.split("\n");
                    for (int j = 2; j < ratingDescriptionLinesArr.length; j++) {
                        String ratingDescription = ratingDescriptionLinesArr[j].substring(8);
                        ratingDescriptions.add(ratingDescription);
                        j += 3;
                    }
                    int yearInt = Integer.parseInt(yearString);
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
                        castMemberArrayList.add(actor);
                        j += 3;
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
                        Movie m = new Movie(id, title, globalTitle, yearString,castMemberArrayList, genres, ageRating, ratingDescriptions);
                        MOVIES.add(m);
                        MainActivity.getmInstanceActivity().writeFile(id, title, globalTitle, yearString,castMemberArrayList, genres, ageRating, ratingDescriptions);
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

    public static MovieManager getInstance(){
        return mm;
    }
}
