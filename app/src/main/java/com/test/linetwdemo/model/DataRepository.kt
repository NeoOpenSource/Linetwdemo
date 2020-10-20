package com.test.linetwdemo.model

import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.test.linetwdemo.api.PlaceholderServer
import com.test.linetwdemo.db.MovieDao
import com.test.linetwdemo.db.MovieTable
import com.test.linetwdemo.decoder.Movie
import com.test.linetwdemo.decoder.MovieList
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.subscribeBy

class DataRepository(private val service: PlaceholderServer,private val movieDao: MovieDao) {

    fun getMovieList2(): Flowable<MovieList> {
        //service.posts().enqueue()
        service.posts().subscribeBy {
            Log.d("e",""+it)
        }
        return service.posts()
    }
    fun getMovieDao(){
        val a = movieDao.getAll()
        Log.d("","")
    }
    fun getMovieDaoSearch(search:String){
        val  liveData = movieDao.findUserWithName(search)

        Log.d("bruceTest","")
    }
    private val query: MutableLiveData<String> = MutableLiveData()
    fun getMovieList(): LiveData<DataResource<MovieList>>?{
        val networkBoundResource = object : NetworkBoundResource() {
            override fun loadFromDb(): LiveData<List<MovieTable>> {
//                Transformations.switchMap(repoDao.search(query),
//                    Function<Any?, LiveData<List<Any?>?>?> { searchData ->
//                        if (searchData == null) {
//                            AbsentLiveData.create()
//                        } else {
//                            repoDao.loadOrdered(searchData.repoIds)
//                        }
//                    })
//                return Transformations.switchMap(query, Function<String,LiveData<MovieList>> {
//                    MutableLiveData(null)
//                })
                return movieDao.getAll()
            }

            override fun shouldFetch(data: MovieList): Boolean {
                return false
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
        return null
    }

}