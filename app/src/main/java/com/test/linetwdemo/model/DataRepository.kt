package com.test.linetwdemo.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.linetwdemo.api.PlaceholderServer
import com.test.linetwdemo.db.MovieDao
import com.test.linetwdemo.db.MovieTable
import com.test.linetwdemo.decoder.MovieList
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.subscribeBy

class DataRepository(private val service: PlaceholderServer,private val movieDao: MovieDao) {

    private val networkBoundResource = object : NetworkBoundResource() {
        override fun loadFromDb(): List<MovieTable> {
            return movieDao.getAll()
        }

        override fun shouldFetch(data: List<MovieTable>): Boolean {
            return data.isEmpty()
        }
        override fun createCall(): Flowable<MovieList> {
            return service.posts()
        }
        override fun saveCallResult(item: MovieList) {
            val data = item.data.
            map { movie ->  MovieTable(0,movie.drama_id,movie.name,movie.total_views,movie.created_at,movie.thumb,movie.rating)}
            movieDao.insertMovieList(data)
            return movieDao.insertMovieList(data)
        }

    }
    fun getMovieList2(): Flowable<MovieList> {
        //service.posts().enqueue()
        service.posts().subscribeBy {
            Log.d("e",""+it)
        }
        return service.posts()
    }
    fun getMovieDao(){
        val a = movieDao.getAll()
    }
    fun getMovieDaoSearch(search:String){
        val  liveData = movieDao.findUserWithName(search)
    }

    fun getMovieList(): LiveData<DataResource<MovieList>>{
        networkBoundResource.checkDb()
        return networkBoundResource.asLiveData()
    }

}