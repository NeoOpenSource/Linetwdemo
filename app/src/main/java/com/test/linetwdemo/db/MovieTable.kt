package com.test.linetwdemo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class MovieTable constructor(
    @ColumnInfo(name = "key_id")
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "drama_id") val dramaId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "total_views") val totalViews: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "thumb") val thumb: String,
    @ColumnInfo(name = "rating") val rating: String
){

}

