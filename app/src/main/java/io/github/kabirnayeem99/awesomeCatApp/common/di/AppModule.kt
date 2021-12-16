package io.github.kabirnayeem99.awesomeCatApp.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.kabirnayeem99.awesomeCatApp.BuildConfig
import io.github.kabirnayeem99.awesomeCatApp.common.constants.CATS_API_BASE_URL
import io.github.kabirnayeem99.awesomeCatApp.common.utility.DispatcherProvider
import io.github.kabirnayeem99.awesomeCatApp.data.dataSources.remoteDataSource.CatFactsApi
import io.github.kabirnayeem99.awesomeCatApp.data.dataSources.remoteDataSource.CatRemoteDataSource
import io.github.kabirnayeem99.awesomeCatApp.data.repositories.DefaultCatRepository
import io.github.kabirnayeem99.awesomeCatApp.domain.repositories.CatRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CATS_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOpenLibraryBookDataSource(retrofit: Retrofit): CatFactsApi {
        return retrofit.create(CatFactsApi::class.java)
    }

    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Provides
    fun provideCoroutineScope(dispatcher: DispatcherProvider): CoroutineScope {
        return CoroutineScope(SupervisorJob() + dispatcher.default)
    }

    @Singleton
    @Provides
    fun provideCatRepository(
        remoteDataSource: CatRemoteDataSource,
        externalScope: CoroutineScope
    ): CatRepository {
        return DefaultCatRepository(remoteDataSource, externalScope)
    }
}