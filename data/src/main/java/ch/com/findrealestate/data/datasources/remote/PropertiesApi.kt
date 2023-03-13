package ch.com.findrealestate.data.datasources.remote

import ch.com.findrealestate.data.models.DetailPropertyApiModel
import ch.com.findrealestate.data.models.PropertyApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface PropertiesApi {
    @GET("properties")
    suspend fun getProperties(): PropertyApiModel

    @GET("properties/{id}")
    suspend fun getDetailProperty(@Path("id") propertyId:String): DetailPropertyApiModel
}
