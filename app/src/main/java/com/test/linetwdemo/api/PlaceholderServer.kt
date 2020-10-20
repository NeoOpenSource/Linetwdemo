package com.test.linetwdemo.api

import com.test.linetwdemo.decoder.MovieList
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface PlaceholderServer {
    @GET("/interview/dramas-sample.json")
    fun posts(): Flowable<MovieList>
}