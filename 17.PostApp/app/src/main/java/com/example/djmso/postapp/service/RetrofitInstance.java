package com.example.djmso.postapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static String URL = "https://jsonplaceholder.typicode.com/";

    public static PostAppService getService(){
      if(retrofit == null){
          retrofit = new Retrofit
                  .Builder()
                  .baseUrl(URL)
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();
      }

      return retrofit.create(PostAppService.class);
    }
}
