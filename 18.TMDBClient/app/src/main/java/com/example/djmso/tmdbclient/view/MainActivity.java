package com.example.djmso.tmdbclient.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.djmso.tmdbclient.BuildConfig;
import com.example.djmso.tmdbclient.R;
import com.example.djmso.tmdbclient.adapter.MovieAdapter;
import com.example.djmso.tmdbclient.model.Movie;
import com.example.djmso.tmdbclient.model.MovieDBResponse;
import com.example.djmso.tmdbclient.service.MovieDataService;
import com.example.djmso.tmdbclient.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMDB Popular Movies Today");

        getPopuparMovies();

        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopuparMovies();
            }
        });
    }

    private void getPopuparMovies() {
        final MovieDataService movieDataService = RetrofitInstance.getService();

        Call<MovieDBResponse> call = movieDataService.getPopularMovies(BuildConfig.TMDB_API_KEY);

        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                MovieDBResponse movieDBResponse = response.body();

                if(movieDBResponse != null && movieDBResponse.getMovies() != null){
                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    showRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void showRecyclerView() {
        recyclerView = findViewById(R.id.rvMovies);

        movieAdapter = new MovieAdapter(this, movies);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }
}
