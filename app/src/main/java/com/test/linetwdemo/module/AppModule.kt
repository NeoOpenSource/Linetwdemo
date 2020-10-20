package com.test.linetwdemo.module

import com.test.linetwdemo.api.Config
import com.test.linetwdemo.api.PlaceholderServer
import com.test.linetwdemo.db.MovieDb
//import com.test.linetwdemo.db.MovieDb
import com.test.linetwdemo.model.DataRepository
import com.test.linetwdemo.ui.main.MainViewModel
import com.test.linetwdemo.utils.LoggerInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit




val mainVmModule = module {
    viewModel { MainViewModel(DataRepository(createRetrofit(Config.URL),get())) }
}

val apiModule = module {
    //single { createOkHttpClient() }
    //single { createRetrofit<PlaceholderServer>(get()) }
    single { MovieDb.getInstance(get()) }
    single { get<MovieDb>().repoDao() }
}

val diModules = listOf(
    apiModule,
    mainVmModule,
)

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(LoggerInterceptor())
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        .build()
}


inline fun <reified T> createRetrofit(serverUrl: String): T {

    val retrofit = Retrofit.Builder()
        .baseUrl(serverUrl)
        .client(createOkHttpClient())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}
