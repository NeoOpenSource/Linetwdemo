package com.test.linetwdemo.db

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.test.linetwdemo.decoder.Movie
import com.test.linetwdemo.decoder.MovieList
import com.test.linetwdemo.model.DataResource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class MovieDbTransform {
    private val result: MediatorLiveData<DataResource<MovieList>> =
        MediatorLiveData<DataResource<MovieList>>()
    private val compositeDisposable = CompositeDisposable()
    fun readDd(search: String = ""): MovieDbTransform{
        clear()
        result.value = DataResource.loading(MovieList(ArrayList()))
        compositeDisposable.add(readDb(search))
        return this
    }

    private fun readDb(search: String = ""): Disposable {
        return Flowable.create<List<MovieTable>>({
            it.onNext(this.readDao(search))
        }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(onNext = {
                toMovieList(it)
            })
    }
    private fun toMovieList(movieTable:List<MovieTable>){
        val data = ArrayList<Movie>(movieTable.size)
        movieTable.forEach {
            data.add(Movie(it.dramaId,it.name,it.totalViews,it.createdAt,it.thumb,it.rating))
        }
        result.value = DataResource.success(MovieList(data))
    }

    fun asLiveData(): LiveData<DataResource<MovieList>> {
        return result
    }

    fun clear() {
        compositeDisposable.clear()
    }

    @NonNull
    @MainThread
    protected abstract fun readDao(search: String): List<MovieTable>

}