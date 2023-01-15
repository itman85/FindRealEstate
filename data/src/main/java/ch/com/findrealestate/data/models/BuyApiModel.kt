package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class BuyApiModel(
    @SerializedName("area")
    val area: String? = null,
    @SerializedName("interval")
    val interval: String? = null,
    @SerializedName("price")
    val price: Long? = null
)
