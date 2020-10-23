package com.test.linetwdemo

import com.test.linetwdemo.db.MovieTable
import com.test.linetwdemo.decoder.MovieList
import com.test.linetwdemo.model.NetworkBoundResource
import com.test.linetwdemo.utils.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Flowable

class NetworkBoundResourceTest(baseSchedulerProvider: BaseSchedulerProvider) : NetworkBoundResource(baseSchedulerProvider
) {
    override fun loadFromDb(): List<MovieTable> {
        val testData = ArrayList<MovieTable>()
        testData.add(
            MovieTable(
                1,
                "dramaId",
                "name",
                "totalView",
                "createdAt",
                "thumb",
                "rating"
            )
        )
        return testData
    }

    override fun shouldFetch(data: List<MovieTable>): Boolean {
        return data.isEmpty()
    }

    override fun createCall(): Flowable<MovieList> {
        TODO("Not yet implemented")
    }

    override fun saveCallResult(item: MovieList) {
        TODO("Not yet implemented")
    }
}