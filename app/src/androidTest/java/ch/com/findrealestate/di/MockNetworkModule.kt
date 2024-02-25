package ch.com.findrealestate.di

import ch.com.findrealestate.data.datasources.remote.PropertiesApi
import ch.com.findrealestate.data.di.NetworkModule
import ch.com.findrealestate.data.models.DetailPropertyApiModel
import ch.com.findrealestate.data.models.PropertyApiModel
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.MockK
import io.mockk.mockk
import io.mockk.spyk
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class MockNetworkModule {

    @Provides
    @Singleton
    fun provideMockRetrofit(mockWebServer: MockWebServer): Retrofit {
        val baseUrl = mockWebServer.url("/")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMockApiService(
        retrofit: Retrofit
    ): PropertiesApi =
        retrofit.create(PropertiesApi::class.java)

}
