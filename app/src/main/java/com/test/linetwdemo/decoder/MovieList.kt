package com.test.linetwdemo.decoder

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MovieList(val data: List<Movie>)
@Parcelize
data class Movie(
    val drama_id: String,
    val name: String,
    val total_views: String,
    val created_at: String,
    val thumb: String,
    val rating: String
): Parcelable