package com.example.djmso.tmdbclient.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.djmso.tmdbclient.R;
import com.example.djmso.tmdbclient.model.Movie;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;

    private ImageView movieImage;

    private String image;

    private TextView movieTitle, movieSynopsis, movieRating, movieReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieImage = findViewById(R.id.ivMovieLarge);
        movieTitle = findViewById(R.id.tvMovieTitle);
        movieSynopsis = findViewById(R.id.tvPlotsynopsis);
        movieRating = findViewById(R.id.tvMovieRating);
        movieReleaseDate = findViewById(R.id.tvReleaseDate);

        Intent intent = getIntent();
        if(intent.hasExtra("movie")) {
            movie = intent.getParcelableExtra("movie");
            Toast.makeText(this, movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();

            image = movie.getPosterPath();

            String path = "https://image.tmdb.org/t/p/w500" + image;

            Glide.with(this)
                    .load(path)
                    .placeholder(R.drawable.loading)
                    .into(movieImage);

            getSupportActionBar().setTitle(movie.getTitle());

            movieTitle.setText(movie.getTitle());
            movieSynopsis.setText(movie.getOverview());
            movieRating.setText("" + movie.getVoteAverage());
            movieReleaseDate.setText(movie.getReleaseDate());
        }

    }

}
