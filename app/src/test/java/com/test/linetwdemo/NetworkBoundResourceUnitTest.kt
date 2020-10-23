package com.test.linetwdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.linetwdemo.db.MovieTable
import com.test.linetwdemo.decoder.MovieList
import com.test.linetwdemo.model.NetworkBoundResource
import com.test.linetwdemo.utils.TestSchedulerProvider
import com.test.linetwdemo.utils.TrampolineSchedulerProvider
import io.reactivex.rxjava3.core.Flowable
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
        val networkBoundResource = NetworkBoundResourceTest(schedulerProvider)
        networkBoundResource.checkDb()
        val value = networkBoundResource.asLiveData().value?.data?.data?.isEmpty() ?: true
        Assert.assertFalse(value)
    }
}