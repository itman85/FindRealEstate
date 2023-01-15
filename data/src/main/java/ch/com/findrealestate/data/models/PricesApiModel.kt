package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class PricesApiModel(
    @SerializedName("buy")
    val buy: BuyApiModel? = null,
    @SerializedName("currency")
    val currency: String? = null,
    // todo: need check what type of rent??
    @SerializedName("rent")
    val rent: Any? = null
)
