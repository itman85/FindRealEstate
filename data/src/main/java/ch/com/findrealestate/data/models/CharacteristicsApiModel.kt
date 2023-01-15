package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class CharacteristicsApiModel(
    @SerializedName("livingSpace")
    val livingSpace: Int? = null,
    @SerializedName("lotSize")
    val lotSize: Int? = null,
    @SerializedName("numberOfRooms")
    val numberOfRooms: Double? = null,
    @SerializedName("totalFloorSpace")
    val totalFloorSpace: Int? = null
)
