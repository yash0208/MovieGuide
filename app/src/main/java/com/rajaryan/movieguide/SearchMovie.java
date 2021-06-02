package com.rajaryan.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchMovie extends AppCompatActivity {
    ImageButton b;
    EditText et;
    String year;
    String rating;
    String plot;
    String length,img;
    String name;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        b=findViewById(R.id.b);
        et=findViewById(R.id.et);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = et.getText().toString();
                OkHttpClient client = new OkHttpClient();
                status="s";
                Request request = new Request.Builder()
                        .url("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/" + search)
                        .get()
                        .addHeader("x-rapidapi-key", "87e6741345mshc3e1dafdd5c1223p122e58jsn3c2c70d40312")
                        .addHeader("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String myresponse = response.body().string();
                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject json = new JSONObject(myresponse);
                            name = json.getString("title");
                            Log.e("name", name);
                            img = json.getString("poster");
                            year = json.getString("year");
                            rating = json.getString("rating");
                            plot = json.getString("plot");
                            length = json.getString("length");
                            status="y";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("res", myresponse);
                    }
                });
                TextView t1 = findViewById(R.id.t1);
                TextView t2 = findViewById(R.id.t2);
                TextView t3 = findViewById(R.id.t3);
                TextView t4 = findViewById(R.id.t4);
                TextView t5 = findViewById(R.id.t5);
                if (img!=null) {
                    ImageView imageView = findViewById(R.id.im1);
                    Picasso.get()
                            .load(img)
                            .into(imageView);
                }
                if (name!=null) {
                    t1.setText("Name : " + name);
                }
                if (year!=null) {
                    t2.setText("Year : " + year);
                }
                if (rating!=null) {
                    t3.setText("Rating : " + rating);
                }
                if (plot!=null) {
                    t4.setText("Plot : " + plot);
                }
                if (length!=null) {
                    t5.setText("Length: " + length);
                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

}