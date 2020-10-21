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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


abstract class NetworkBoundResource @MainThread internal constructor() {
    private val result: MediatorLiveData<DataResource<MovieList>> = MediatorLiveData<DataResource<MovieList>>()
    private val compositeDisposable = CompositeDisposable()
    public fun checkDb(){
        result.value = DataResource.loading(MovieList(ArrayList()))
        compositeDisposable.add(readDb())
    }
    private fun readDb(): Disposable {
        return Flowable.create<List<MovieTable>>({
            it.onNext(this.loadFromDb())
        }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(onNext = {
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
            result.value = DataResource.error(MovieList(ArrayList()),"error")
        },onNext = {
            saveCallResult(it)
        },onComplete = {
            compositeDisposable.add(readDb())
        }))
    }

    fun asLiveData(): LiveData<DataResource<MovieList>> {
        return result
    }


    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): List<MovieTable>

    //
    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(@Nullable data: List<MovieTable>): Boolean

    // Called to create the API call.
    @NonNull
    @MainThread
    public abstract fun createCall(): Flowable<MovieList>

    @WorkerThread
    protected abstract fun saveCallResult(@NonNull item: MovieList)
//
//    // Called to save the result of the API response into the database
//    @WorkerThread
//    protected abstract fun saveCallResult(@NonNull item: RequestType)
//

}