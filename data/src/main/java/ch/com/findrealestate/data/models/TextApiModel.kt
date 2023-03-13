package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class TextApiModel(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null
)
