package com.example.djmso.tmdbclient.service;

import com.example.djmso.tmdbclient.model.MovieDBResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {
    @GET("movie/popular")
    Observable<MovieDBResponse> getPopularMoviesWithRx(@Query("api_key") String apiKey);
}
