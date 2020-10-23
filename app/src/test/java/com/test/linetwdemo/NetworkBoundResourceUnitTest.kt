package com.test.linetwdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.linetwdemo.decoder.MovieList
import com.test.linetwdemo.utils.TestSchedulerProvider
import com.test.linetwdemo.utils.TrampolineSchedulerProvider
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class NetworkBoundResourceUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testScheduler = TestScheduler()
    private var schedulerProvider = TrampolineSchedulerProvider()
    private var testSchedulerProvider = TestSchedulerProvider(testScheduler)

    @Test
    fun `test_get_data_logic`() {
        val networkBoundResource = TestNetworkBoundResourceHaveData(schedulerProvider)
        networkBoundResource.checkDb()
        val value = networkBoundResource.asLiveData().value?.data?.data?.isEmpty() ?: true
        Assert.assertFalse(value)
    }


    @Test
    fun `test_not_data_logic`() {
        var movieList = MovieList(emptyList())
        val networkBoundResource = object :TestNetworkBoundResourceNotData(schedulerProvider){
            override fun saveCallResult(item: MovieList) {
                movieList = item
            }
        }
        networkBoundResource.checkDb()
        val value = movieList.data.isEmpty()
        Assert.assertFalse(value)
    }
}