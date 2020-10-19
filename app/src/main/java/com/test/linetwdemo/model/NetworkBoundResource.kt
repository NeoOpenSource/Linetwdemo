package com.test.linetwdemo.model

import android.os.AsyncTask
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.test.linetwdemo.api.ApiResponse


abstract class NetworkBoundResource<ResultType, RequestType> @MainThread internal constructor() {
//    private val result: MediatorLiveData<DataResource<ResultType>> = MediatorLiveData<DataResource<ResultType>>()
//
//    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
//        val apiResponse: LiveData<ApiResponse<RequestType>> = createCall()!!
//        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
//        result.addSource(dbSource, object : Observer<ResultType>() {
//            fun onChanged(@Nullable newData: ResultType) {
//                result.setValue(Resource.loading(newData))
//            }
//        })
//        result.addSource<ApiResponse<RequestType>>(
//            apiResponse,
//            object : Observer<ApiResponse<RequestType>?>() {
//                fun onChanged(@Nullable response: ApiResponse<RequestType>) {
//                    result.removeSource<ApiResponse<RequestType>>(apiResponse)
//                    result.removeSource(dbSource)
//                    if (response.isSuccessful()) {
//                        saveResultAndReInit(response)
//                    } else {
//                        onFetchFailed()
//                        result.addSource(dbSource, object : Observer<ResultType>() {
//                            fun onChanged(@Nullable newData: ResultType) {
//                                result.setValue(Resource.error(newData, response.errorMessage))
//                            }
//                        })
//                    }
//                }
//            })
//    }
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
//    @NonNull
//    @MainThread
//    protected abstract fun loadFromDb(): LiveData<ResultType>
//
//    // Called with the data in the database to decide whether it should be
//    // fetched from the network.
//    @MainThread
//    protected abstract fun shouldFetch(@Nullable data: ResultType): Boolean
//
//    // Called to create the API call.
//    @NonNull
//    @MainThread
//    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>?>?
//
//    // Called to save the result of the API response into the database
//    @WorkerThread
//    protected abstract fun saveCallResult(@NonNull item: RequestType)
//
//    init {
//        result.setValue(DataResource.< ResultType > loading < ResultType ? > null)
//        val dbSource = loadFromDb()
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
//    }
}