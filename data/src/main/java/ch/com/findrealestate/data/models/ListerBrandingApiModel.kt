package ch.com.findrealestate.data.models

import com.google.gson.annotations.SerializedName

data class ListerBrandingApiModel(
    @SerializedName("adActive")
    val adActive: Boolean? = null,
    @SerializedName("address")
    val address: AddressApiModel? = null,
    @SerializedName("isPremiumBranding")
    val isPremiumBranding: Boolean? = null,
    @SerializedName("isQualityPartner")
    val isQualityPartner: Boolean? = null,
    @SerializedName("legalName")
    val legalName: String? = null,
    @SerializedName("logoUrl")
    val logoUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("profilePageUrlKeyword")
    val profilePageUrlKeyword: String? = null
) {
    data class AddressApiModel(
        @SerializedName("country")
        val country: String? = null,
        @SerializedName("locality")
        val locality: String? = null,
        @SerializedName("postalCode")
        val postalCode: String? = null,
        @SerializedName("region")
        val region: String? = null,
        @SerializedName("street")
        val street: String? = null
    )
}
