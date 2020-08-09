package com.example.booklistactivity;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil() {
    }

    public static final String BASE_API_URL =
            "https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY = "q";
    public static final String KEY = "key";
    public static final String API_KEY = "AIzaSyDqcmBdH2tM7HPu9IBbQgzsgtUWlbylPhM";


    public static URL buildUrl(String title) {
        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        try {
            url = new URL(uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream stream = connection.getInputStream();
            //convert the stream to a string
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A"); //READ ALLL
            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            connection.disconnect();
        }

    }

    public static ArrayList<Book> getBookFromJson(String json) {
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHEDATE = "publishedDate";
        final String VOLUMEINFO = "volumeInfo";
        final String ITEMS = "items";
        final String DESCRIPTION="description";
        final String IMAGELINKS="imageLinks";
        final String THUMBNAILS="thumbnails";

        ArrayList<Book> books = new ArrayList<Book>();

        try {
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);
            int numberOfBooks = arrayBooks.length();
            for (int i = 0; i < numberOfBooks; i++) {
                JSONObject bookJSON = arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUMEINFO);
                //JSONObject imageLinks=volumeInfoJSON.getJSONObject(IMAGELINKS);
                int authorNum=volumeInfoJSON.getJSONArray(AUTHORS).length();
                String[] authors =new String[authorNum];
                for (int j = 0; j <authorNum; j++) {
                   authors[j]=volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
                }
                Book book=new Book(
                        bookJSON.getString(ID),
                        volumeInfoJSON.getString(TITLE),
                        (volumeInfoJSON.isNull(SUBTITLE)? "" :volumeInfoJSON.getString(SUBTITLE)),
                        authors,
                        volumeInfoJSON.getString(PUBLISHER),
                        volumeInfoJSON.getString(PUBLISHEDATE),
                        volumeInfoJSON.getString(DESCRIPTION));
                books.add(book);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;

    }
}
