package com.test.linetwdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MovieTable::class], version = 1)
abstract class MovieDb : RoomDatabase() {
    abstract fun repoDao(): MovieDao?
    companion object {
        private var INSTANCE: MovieDb? = null
        fun getInstance(context: Context): MovieDb? {
            if (INSTANCE == null) {
                synchronized(MovieDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MovieDb::class.java, "Movie"
                    ).build()
                }
            }
            return INSTANCE
        }
        fun destroy() {
            INSTANCE = null
        }
    }
}