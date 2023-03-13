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
    val totalFloorSpace: Int? = null,
    @SerializedName("hasFireplace")
    val hasFireplace: Boolean? = null,
    @SerializedName("yearBuilt")
    val yearBuilt: Int? = null,
    @SerializedName("isMinergieGeneral")
    val isMinergieGeneral:Boolean? = null,
    @SerializedName("hasSwimmingPool")
    val hasSwimmingPool:Boolean? = null,
    @SerializedName("ceilingHeight")
    val ceilingHeight:Double? = null,
    @SerializedName("distanceShop")
    val distanceShop:Int? = null,
    @SerializedName("hasGarage")
    val hasGarage:Boolean? = null,
    @SerializedName("hasParking")
    val hasParking: Boolean? = null,
    @SerializedName("hasBalcony")
    val hasBalcony: Boolean? = null,
    @SerializedName("hasCableTv")
    val hasCableTv:Boolean? = null,
    @SerializedName("hasNiceView")
    val hasNiceView:Boolean? = null,
    @SerializedName("hasElevator")
    val hasElevator:Boolean? = null
)

fun CharacteristicsApiModel.toMapData() = mapOf(
    "Living Space" to this.livingSpace.toString(),
    "Lot Size" to this.lotSize.toString(),
    "Number Of Rooms" to this.numberOfRooms.toString(),
    "Total Floor Space" to this.totalFloorSpace.toString(),
    "Fire Place" to if(this.hasFireplace == true) "Yes" else "No",
    "Year Built" to this.yearBuilt.toString(),
    "Minergie General" to if(this.isMinergieGeneral == true) "Yes" else "No",
    "Swimming Pool" to if(this.hasSwimmingPool == true) "Yes" else "No",
    "Ceiling Height" to this.ceilingHeight.toString(),
    "Distance Shop" to this.distanceShop.toString(),
    "Garage" to if(this.hasGarage == true) "Yes" else "No",
    "Parking" to if(this.hasParking == true) "Yes" else "No",
    "Balcony" to if(this.hasBalcony == true) "Yes" else "No",
    "Cable TV" to if(this.hasCableTv == true) "Yes" else "No",
    "Nice View" to if(this.hasNiceView == true) "Yes" else "No",
    "Elevator" to if(this.hasElevator == true) "Yes" else "No",
)
