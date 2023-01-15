package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class ListingTypeApiModel(
    @SerializedName("type")
    val type: String? = null
)
