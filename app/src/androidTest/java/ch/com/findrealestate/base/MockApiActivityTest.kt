package ch.com.findrealestate.base

import ch.com.findrealestate.mock.ApiMocker
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Inject

abstract class MockApiActivityTest<T : ApiMocker> : BaseActivityTest() {

    @Inject
    protected lateinit var apiMocker: T

    @Inject
    protected lateinit var mockWebServer: MockWebServer

    override fun setUp() {
        mockWebServer.start()
    }

    override fun tearDown() {
        if (this::mockWebServer.isInitialized)
            mockWebServer.shutdown()
    }
}
