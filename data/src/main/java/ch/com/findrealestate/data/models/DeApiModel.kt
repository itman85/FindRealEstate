package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class DeApiModel(
    @SerializedName("attachments")
    val attachments: List<AttachmentApiModel?>? = null,
    @SerializedName("text")
    val text: TextApiModel? = null,
    @SerializedName("urls")
    val urls: List<UrlApiModel?>? = null
)
