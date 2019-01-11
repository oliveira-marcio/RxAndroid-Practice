package com.example.djmso.postapp.service;

import com.example.djmso.postapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostAppService {
    @POST("posts")
    Call<User> getResult(@Body User user);
}
