package com.example.movieapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://movies6.herokuapp.com/movie";
    public static final String TAG = "MainActivity";
    private ListView lv;
    private OkHttpClient client;
    private MovieAdapter movieAdapter;
    //Gson
    private ArrayList<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        movies = new ArrayList<Movie>();
        movieAdapter = new MovieAdapter(MainActivity.this,R.layout.movie_row,movies);
        lv = findViewById(R.id.MainActivity_LV_movies);
        lv.setAdapter(movieAdapter);
        getAllMovies();



        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
                b.setTitle("Delete '" + movies.get(position).getTitle() + "'");
                b.setMessage("Do you want to delete this movie from the list?");
                b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        movies.remove(position);
                        movieAdapter.notifyDataSetChanged();
                        Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
                b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Movie will remain", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
                return false;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(getBaseContext(), );
                // setI
            }
        });
    }

    private void getAllMovies() {

        Request requestGetAllMovies = new Request.Builder().get().url(BASE_URL).build();

        client.newCall(requestGetAllMovies).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // shortcut : ctrl + alt + c.
                Log.e(TAG,e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String json = response.body().string();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    Type movieArrayListType = new TypeToken<ArrayList<Movie>>(){}.getType();
                    movies = gson.fromJson(json,movieArrayListType);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieAdapter = new MovieAdapter(MainActivity.this,R.layout.movie_row,movies);
                            lv.setAdapter(movieAdapter);
                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}