package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText etTitle=findViewById(R.id.etTitle);
        final EditText etAuthor=findViewById(R.id.etAuthor);
        final EditText etPublisher=findViewById(R.id.etPublisher);
        final EditText etIsbn=findViewById(R.id.etIsbn);
        final Button buttonSearch=findViewById(R.id.btnSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=etTitle.getText().toString().trim();
                String author=etAuthor.getText().toString().trim();
                String publisher=etPublisher.getText().toString().trim();
                String isbn=etIsbn.getText().toString().trim();

                if (title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()){
                    String message=getString(R.string.input_message);
                    Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show();

                }
                else {
                    URL queryUrl=ApiUtil.buildUrl(title,author,publisher,isbn);

                    //sharedPreference
                    Context context=getApplicationContext();
                    int position=SharedPUtil.getPreferenceInt(context,SharedPUtil.POSITION);
                    if (position==0 || position==5){
                        position=1;
                    }
                    else {
                        position++;
                    }
                    String key=SharedPUtil.QUERY+ String.valueOf(position);
                    String value=title + "," +author + "," +publisher + ","+isbn;
                    SharedPUtil.setPreferenceString(context,key,value);
                    SharedPUtil.setPreferenceInt(context,SharedPUtil.POSITION,position);
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("Query",queryUrl);
                    startActivity(intent);
                }
            }
        });
    }
}