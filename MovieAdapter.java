package com.example.movieapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movies;
    public Context context;
    private int layout;

    public MovieAdapter(Context context, int layout, List<Movie> movies) {
        super(context, layout, movies);
        this.context = context;
        this.layout = layout;
        this.movies = movies;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if (view == null){
            view = LayoutInflater.from(context).inflate(layout,parent,false);
        }

        ImageView image = view.findViewById(R.id.movieRow_IV_image);
        TextView title = view.findViewById(R.id.movieRow_TV_title);
        TextView rating = view.findViewById(R.id.movieRow_TV_rating);
        TextView actors = view.findViewById(R.id.movieRow_TV_actors);

        Movie m = movies.get(i);
        String url = m.getImage();
        title.setText(m.getTitle());
        actors.setText(m.getActors());
        rating.setText(String.valueOf(m.getRating()));
        Picasso.get().load(url).into(image);

        return view;


    }
}
