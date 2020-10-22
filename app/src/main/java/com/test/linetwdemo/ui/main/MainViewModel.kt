package com.test.linetwdemo.ui.main

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.test.linetwdemo.decoder.MovieList
import com.test.linetwdemo.model.DataRepository
import com.test.linetwdemo.model.DataResource
import com.test.linetwdemo.model.NetworkBoundResource
import com.test.linetwdemo.model.Status
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainViewModel(private val repo: DataRepository) : ViewModel() {
    val result: MediatorLiveData<DataResource<MovieList>> = MediatorLiveData<DataResource<MovieList>>()
    fun getApi(){
        val liveDate = repo.getMovieList()
        result.addSource(liveDate){
            if (it.status != Status.LOADING){
                result.removeSource(liveDate)
            }
            result.value = it
        }

        //repo.getMovieList2()
        //repo.getMovieDao()
        //repo.getMovieDaoSearch("獅子")
    }
    fun searchMovie(search:String){
        val liveDate = repo.getMovieDaoSearch(search)
        result.addSource(liveDate){
            if (it.status != Status.LOADING){
                result.removeSource(liveDate)
            }
            result.value = it
        }
    }
}