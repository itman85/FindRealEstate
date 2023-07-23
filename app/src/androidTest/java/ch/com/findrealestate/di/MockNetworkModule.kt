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

    /* use MockWebServer so will not need to mock PropertiesApi
    @Provides
    @Singleton
    fun provideApiService():PropertiesApi{
        // cannot use mockk() as there was issue here https://github.com/mockk/mockk/issues/1035
        return spyk(object : PropertiesApi{
            override suspend fun getProperties(): PropertyApiModel {
                return PropertyApiModel()
            }

            override suspend fun getDetailProperty(propertyId: String): DetailPropertyApiModel {
                return DetailPropertyApiModel()
            }
        })
    }
     */

    @Provides
    @Singleton
    fun provideMockRetrofit(mockWebServer: MockWebServer): Retrofit {
        val baseUrl = mockWebServer.url("/").toString()
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
