package com.test.linetwdemo.api

import com.test.linetwdemo.decoder.MovieList
import retrofit2.Call
import retrofit2.http.GET

interface PlaceholderServer {
    @GET("/interview/dramas-sample.json")
    fun posts(): Call<MovieList>
}