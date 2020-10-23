package com.test.linetwdemo

import androidx.annotation.NonNull
import com.test.linetwdemo.db.MovieTable
import com.test.linetwdemo.decoder.Movie
import com.test.linetwdemo.decoder.MovieList
import com.test.linetwdemo.model.NetworkBoundResource
import com.test.linetwdemo.utils.BaseSchedulerProvider
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableOnSubscribe


class TestNetworkBoundResourceHaveData(baseSchedulerProvider: BaseSchedulerProvider) :
    NetworkBoundResource(
        baseSchedulerProvider
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

abstract class TestNetworkBoundResourceNotData(baseSchedulerProvider: BaseSchedulerProvider) :
    NetworkBoundResource(
        baseSchedulerProvider
    ) {
    override fun loadFromDb(): List<MovieTable> {
        return emptyList()
    }

    override fun shouldFetch(data: List<MovieTable>): Boolean {
        return data.isEmpty()
    }

    override fun createCall(): Flowable<MovieList> {
        return Flowable.create(FlowableOnSubscribe<MovieList> { e ->
            val data = ArrayList<Movie>()
            data.add(Movie("dramaId", "name", "total", "created", "thumb", "rating"))
            val movieList = MovieList(data)
            e.onNext(movieList)
        }, BackpressureStrategy.ERROR)
    }

    abstract override fun saveCallResult(@NonNull item: MovieList)
}