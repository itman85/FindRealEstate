package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class ListingApiModel(
    @SerializedName("address")
    val address: AddressXApiModel? = null,
    @SerializedName("categories")
    val categories: List<String>? = null,
    @SerializedName("characteristics")
    val characteristics: CharacteristicsApiModel? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("lister")
    val lister: ListerApiModel? = null,
    @SerializedName("localization")
    val localization: LocalizationApiModel? = null,
    @SerializedName("offerType")
    val offerType: String? = null,
    @SerializedName("prices")
    val prices: PricesApiModel? = null
)
