package com.test.linetwdemo.decoder

data class MovieList(val data: List<Movie>)
data class Movie(
    val drama_id: String,
    val name: String,
    val total_views: String,
    val created_at: String,
    val thumb: String,
    val rating: String
)