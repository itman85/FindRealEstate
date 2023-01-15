package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class AddressXApiModel(
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("geoCoordinates")
    val geoCoordinates: GeoCoordinatesApiModel? = null,
    @SerializedName("locality")
    val locality: String? = null,
    @SerializedName("postalCode")
    val postalCode: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("street")
    val street: String? = null
)
