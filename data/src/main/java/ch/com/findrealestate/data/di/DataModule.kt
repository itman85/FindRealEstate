package ch.com.findrealestate.data.di

import android.content.Context
import ch.com.findrealestate.data.BuildConfig
import ch.com.findrealestate.data.database.PropertyDatabase
import ch.com.findrealestate.data.database.dao.PropertyDao
import ch.com.findrealestate.data.datasources.remote.PropertiesApi
import ch.com.findrealestate.data.repositories.PropertiesRepositoryImpl
import ch.com.findrealestate.domain.repositories.PropertiesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPropertiesRepository(repo: PropertiesRepositoryImpl):PropertiesRepository
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule{

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : PropertyDatabase{
        return PropertyDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providePropertyDao(database: PropertyDatabase): PropertyDao {
        return database.propertyDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun apiService(
        retrofit: Retrofit
    ): PropertiesApi =
        retrofit.create(PropertiesApi::class.java)
}