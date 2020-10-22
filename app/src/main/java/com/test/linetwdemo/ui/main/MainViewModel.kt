package com.test.linetwdemo.ui.main
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.test.linetwdemo.decoder.MovieList
import com.test.linetwdemo.model.DataRepository
import com.test.linetwdemo.model.DataResource
import com.test.linetwdemo.model.Status

class MainViewModel(private val repo: DataRepository) : ViewModel() {
    val result: MediatorLiveData<DataResource<MovieList>> = MediatorLiveData<DataResource<MovieList>>()
    fun getApi(){
        val liveDate = repo.getMovieList()
        result.removeSource(liveDate)
        result.addSource(liveDate){
            if (it.status != Status.LOADING){
                result.removeSource(liveDate)
            }
            result.value = it
        }

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