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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Observable<MovieDBResponse> movieDBResponseObservable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMDB Popular Movies Today");

        getPopuparMoviesRx();

        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopuparMoviesRx();
            }
        });
    }


    private void getPopuparMoviesRx() {
        final MovieDataService movieDataService = RetrofitInstance.getService();

        movieDBResponseObservable = movieDataService.getPopularMoviesWithRx(BuildConfig.TMDB_API_KEY);

        compositeDisposable.add(movieDBResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<MovieDBResponse, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> apply(MovieDBResponse movieDBResponse) throws Exception {
                        return Observable.fromArray(movieDBResponse.getMovies().toArray(new Movie[0]));
                    }
                })
                .filter(new Predicate<Movie>() {
                    @Override
                    public boolean test(Movie movie) throws Exception {
                        return movie.getVoteAverage() > 7.0;
                    }
                }) // Now we have a better control of data stream and we can apply filters, for example.
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        movies.add(movie);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        showRecyclerView();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                })
        );
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}
