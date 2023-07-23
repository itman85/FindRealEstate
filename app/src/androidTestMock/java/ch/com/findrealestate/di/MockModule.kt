package ch.com.findrealestate.di

import ch.com.findrealestate.mock.PropertiesApiMocker
import ch.com.findrealestate.mock.PropertiesApiMockerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton
// these modules are available in mock flavor only
@Module
@InstallIn(SingletonComponent::class)
abstract class MockerBindingModule {

    @Binds
    abstract fun bindPropertiesApiMocker(impl: PropertiesApiMockerImpl): PropertiesApiMocker
}


@Module
@InstallIn(SingletonComponent::class)
class MockWebServerModule {

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer()
}
