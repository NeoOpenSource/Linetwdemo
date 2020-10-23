package com.test.linetwdemo.model

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.test.linetwdemo.db.MovieTable
import com.test.linetwdemo.decoder.Movie
import com.test.linetwdemo.decoder.MovieList
import com.test.linetwdemo.utils.BaseSchedulerProvider
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy


abstract class NetworkBoundResource @MainThread internal constructor(private val baseSchedulerProvider: BaseSchedulerProvider) {
    private val result: MediatorLiveData<DataResource<MovieList>> = MediatorLiveData<DataResource<MovieList>>()
    private val compositeDisposable = CompositeDisposable()
    fun checkDb(){
        clear()
        result.value = DataResource.loading(MovieList(ArrayList()))
        compositeDisposable.add(readDb())
    }
    private fun readDb(): Disposable {
        return Flowable.create<List<MovieTable>>({
            it.onNext(this.loadFromDb())
        }, BackpressureStrategy.ERROR)
            .subscribeOn(baseSchedulerProvider.io())
            .observeOn(baseSchedulerProvider.ui()).subscribeBy(onNext = {
                if (shouldFetch(it)) {
                    fetchFromNetwork()
                }else{
                    toMovieList(it)
                }
            })

    }

    private fun toMovieList(movieTable:List<MovieTable>){
        val data = ArrayList<Movie>(movieTable.size)
        movieTable.forEach {
            data.add(Movie(it.dramaId,it.name,it.totalViews,it.createdAt,it.thumb,it.rating))
        }
        result.value = DataResource.success(MovieList(data))
    }
    private fun fetchFromNetwork() {
        compositeDisposable.add(createCall().subscribeBy(onError = {
            result.postValue(DataResource.error(MovieList(ArrayList()),it.message ?: ""))
        },onNext = {
            saveCallResult(it)
        },onComplete = {
            compositeDisposable.add(readDb())
        }))
    }

    fun asLiveData(): LiveData<DataResource<MovieList>> {
        return result
    }

    fun clear(){
        compositeDisposable.clear()
    }
    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): List<MovieTable>


    @MainThread
    protected abstract fun shouldFetch(@Nullable data: List<MovieTable>): Boolean

    @NonNull
    @MainThread
    protected abstract fun createCall(): Flowable<MovieList>

    @WorkerThread
    protected abstract fun saveCallResult(@NonNull item: MovieList)


}