package com.test.linetwdemo.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.test.linetwdemo.model.DataRepository
import com.test.linetwdemo.model.NetworkBoundResource
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainViewModel(private val repo: DataRepository) : ViewModel() {

    fun getApi(){
//        repo.getMovieList().subscribeBy(onError = {
//            Log.d("test","tests"+it)
//        },onSuccess = {
//            Log.d("test","tests"+it)
//        })
        repo.getMovieList()
        //repo.getMovieList2()
        //repo.getMovieDao()
        //repo.getMovieDaoSearch("獅子")
    }
}