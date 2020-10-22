package com.test.linetwdemo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(order: List<MovieTable>)

    @Query("SELECT * FROM MovieTable")
    fun getAll2(): LiveData<List<MovieTable>>

    @Query("SELECT * FROM MovieTable")
    fun getAll(): List<MovieTable>


//    @Query("SELECT * FROM MovieTable WHERE name LIKE :search ")
//    fun findUserWithName(search: String): LiveData<List<MovieTable>>

    @Query("SELECT * FROM MovieTable WHERE name LIKE :search ")
    fun findMovieWithName(search: String): List<MovieTable>
}