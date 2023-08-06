package ch.com.findrealestate.mock

import ch.com.findrealestate.utils.loadResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PropertiesApiMockerImpl @Inject constructor(private val mockWebServer: MockWebServer) :
    PropertiesApiMocker {

    override fun getPropertiesSuccess() {
        val listingDataJson = this.loadResource("api-data/listings.json")
        val mockedResponse = MockResponse().apply {
            if (listingDataJson.isNullOrEmpty()) {
                setResponseCode(404)
            } else {
                setResponseCode(200).setBody(listingDataJson).setBodyDelay(3, TimeUnit.SECONDS)
            }
        }
        mockWebServer.enqueue(mockedResponse)
    }

    override fun getPropertiesFailed() {
        val mockedResponse = MockResponse().apply {
            setResponseCode(404)
        }
        mockWebServer.enqueue(mockedResponse)
    }

    override fun getSimilarPropertiesSuccess() {
        val listingDataJson = this.loadResource("api-data/similar_listings.json")
        val mockedResponse = MockResponse().apply {
            if (listingDataJson.isNullOrEmpty()) {
                setResponseCode(404)
            } else {
                setResponseCode(200).setBody(listingDataJson)
            }
        }
        mockWebServer.enqueue(mockedResponse)
    }

    override fun getDetailPropertySuccess(propertyId: String) {
        val listingDataJson = this.loadResource("api-data/listing_$propertyId.json")
        val mockedResponse = MockResponse().apply {
            if (listingDataJson.isNullOrEmpty()) {
                setResponseCode(404)
            } else {
                setResponseCode(200).setBody(listingDataJson).setBodyDelay(3, TimeUnit.SECONDS)
            }
        }
        mockWebServer.enqueue(mockedResponse)

        /*
           we can use dispatcher to handle mock response for specific url, but no need in this case
           val detailApiDispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when(request.path){
                        "properties/{id}" -> mockedResponse
                        else -> MockResponse().setResponseCode(404)
                    }
                }

            }
            mockWebServer.dispatcher = detailApiDispatcher
            */
    }

    override fun getDetailPropertyFailed(propertyId: String) {
        val mockedResponse = MockResponse().apply {
            setResponseCode(404)
        }
        mockWebServer.enqueue(mockedResponse)
    }


}
