package com.example.sdidebartesti;

import android.os.AsyncTask;
import android.os.StrictMode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MovieManager {
    private static MovieManager mm = new MovieManager();
    ArrayList<Movie> MOVIES;

    private MovieManager(){
        MOVIES = new ArrayList<>();
        String url_FINKINO = "https://www.finnkino.fi/xml/Events/";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
                        int rating = searchRating(title);
                        Movie m1 = new Movie(id, title, rating);
                        System.out.println(title + " " + id);
                        MOVIES.add(m1);
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
    private int searchRating(String name){
        String url_IMDB = "https://imdb-api.com/en/API/Search/k_y3x6e641/"+name;
        int rating = 0;

        return rating;
    }
    public static MovieManager getInstance(){
        return mm;
    }
}
