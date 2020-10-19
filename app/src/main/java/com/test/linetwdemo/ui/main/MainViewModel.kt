package com.test.linetwdemo.ui.main

import androidx.lifecycle.ViewModel
import com.test.linetwdemo.model.DataRepository

class MainViewModel(private val repo: DataRepository) : ViewModel() {

    fun getApi(){
        repo.getMovieList()
    }
}