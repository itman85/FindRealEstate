package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class ListerApiModel(
    @SerializedName("logoUrl")
    val logoUrl: String? = null,
    @SerializedName("phone")
    val phone: String? = null
)
