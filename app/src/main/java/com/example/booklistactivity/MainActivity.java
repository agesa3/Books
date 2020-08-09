package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static androidx.core.view.MenuItemCompat.getActionView;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ProgressBar mLoading;
    private RecyclerView rvBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoading = findViewById(R.id.pb_loading);
        rvBooks = findViewById(R.id.rv_books);
        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvBooks.setLayoutManager(booksLayoutManager);

        try {
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new BookQueryTask().execute(bookUrl);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error", e.getMessage());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booklist_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL bookUrl = ApiUtil.buildUrl(query);
            new BookQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, "Error loading", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class BookQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;

            try {
                result = ApiUtil.getJson(searchURL);
            } catch (IOException e) {
                Log.e("Error", Objects.requireNonNull(e.getMessage()));
            }
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView tvResult=findViewById(R.id.tvResponse);
            TextView tvError = findViewById(R.id.tvError);

            mLoading.setVisibility(View.INVISIBLE);
            if (result == null) {
                rvBooks.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            } else {
                rvBooks.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }

            ArrayList<Book> books = ApiUtil.getBookFromJson(result);
            String resultString = "";

            BooksAdapter adapter = new BooksAdapter(books);
            rvBooks.setAdapter(adapter);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }
    }
}