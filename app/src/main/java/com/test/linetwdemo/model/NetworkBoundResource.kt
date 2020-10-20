package com.test.linetwdemo.model

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.test.linetwdemo.db.MovieTable
import com.test.linetwdemo.decoder.MovieList
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.subscribeBy


abstract class NetworkBoundResource @MainThread internal constructor() {
    private val result: MediatorLiveData<MovieList> = MediatorLiveData<MovieList>()
    private val dbResult: MediatorLiveData<List<MovieTable>> = MediatorLiveData()
    init {
        val dbSource = this.loadFromDb()
//        result.addSource(dbSource) {
//            result.removeSource(dbSource)
//            if (shouldFetch(it)) {
//                fetchFromNetwork(dbSource)
//            }
//        }
        dbResult.addSource(dbSource){
            dbResult.removeSource(dbSource)
        }
        fetchFromNetwork()
//        result.addSource(dbSource, object : Observer<ResultType>() {
//            fun onChanged(@Nullable data: ResultType) {
//                result.removeSource(dbSource)
//                if (shouldFetch(data)) {
//                    fetchFromNetwork(dbSource)
//                } else {
//                    result.addSource(dbSource, object : Observer<ResultType>() {
//                        fun onChanged(@Nullable newData: ResultType) {
//                            result.setValue(Resource.success(newData))
//                        }
//                    })
//                }
//            }
//        })
    }


    private fun fetchFromNetwork() {
        val data = createCall()
        //val apiResponse = LiveDataReactiveStreams.fromPublisher(data)
        createCall().subscribeBy {
            //result.value = it
            //result.postValue(it)
            //dbSource.
            saveCallResult(it)
        }
//        result.addSource(apiResponse){
//            Log.d("b",""+it)
//        }
    }
//
//    @MainThread
//    private fun saveResultAndReInit(response: ApiResponse<RequestType>) {
//        object : AsyncTask<Void?, Void?, Void?>() {
//            protected override fun doInBackground(vararg voids: Void): Void? {
//                saveCallResult(response.body)
//                return null
//            }
//
//            override fun onPostExecute(aVoid: Void?) {
//                // we specially request a new live data,
//                // otherwise we will get immediately last cached value,
//                // which may not be updated with latest results received from network.
//                result.addSource(loadFromDb(), object : Observer<ResultType>() {
//                    fun onChanged(@Nullable newData: ResultType) {
//                        result.setValue(Resource.success(newData))
//                    }
//                })
//            }
//        }.execute()
//    }
//
//    // Called when the fetch fails. The child class may want to reset components
//    // like rate limiter.
//    protected fun onFetchFailed() {}
//
//    fun asLiveData(): LiveData<DataResource<ResultType>> {
//        return result
//    }
//

    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): LiveData<List<MovieTable>>
//
    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(@Nullable data: MovieList): Boolean

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