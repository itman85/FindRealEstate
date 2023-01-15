package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class GeoCoordinatesApiModel(
    @SerializedName("latitude")
    val latitude: Double? = null,
    @SerializedName("longitude")
    val longitude: Double? = null
)
