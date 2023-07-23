package ch.com.findrealestate.mock

import ch.com.findrealestate.utils.loadResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Inject

class PropertiesApiMockerImpl @Inject constructor(private val mockWebServer: MockWebServer) :
    PropertiesApiMocker {

    override fun getPropertiesSuccess() {
        TODO("Not yet implemented")
    }

    override fun getDetailPropertySuccess(propertyId: String) {
        val listingDataJson = this.loadResource("api-data/listing_$propertyId.json")
        val mockedResponse = MockResponse().apply {
            if (listingDataJson.isNullOrEmpty()) {
                setResponseCode(400)
            } else {
                setResponseCode(200).setBody(listingDataJson)
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


}
