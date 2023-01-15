package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class AttachmentApiModel(
    @SerializedName("file")
    val file: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null
)
