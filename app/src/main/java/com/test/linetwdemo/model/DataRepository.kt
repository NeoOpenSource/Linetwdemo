package com.test.linetwdemo.model

import com.test.linetwdemo.api.PlaceholderServer
import com.test.linetwdemo.decoder.MovieList
import retrofit2.Call

class DataRepository(private val service: PlaceholderServer) {
    fun getMovieList(): Call<MovieList> {
        return service.posts()
    }
}