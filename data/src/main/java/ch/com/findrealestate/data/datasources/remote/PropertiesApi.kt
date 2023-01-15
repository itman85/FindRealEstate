package ch.com.findrealestate.data.datasources.remote

import ch.com.findrealestate.data.models.PropertyApiModel
import retrofit2.http.GET

interface PropertiesApi {
    @GET("properties")
    suspend fun getProperties(): PropertyApiModel
}
