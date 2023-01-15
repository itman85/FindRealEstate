package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class LocalizationApiModel(
    @SerializedName("de")
    val de: DeApiModel? = null,
    @SerializedName("primary")
    val primary: String? = null
)
