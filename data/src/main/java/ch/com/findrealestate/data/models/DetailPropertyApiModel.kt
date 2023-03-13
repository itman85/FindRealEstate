package ch.com.findrealestate.data.models

import ch.com.findrealestate.data.mapper.asAddress
import ch.com.findrealestate.domain.entity.PropertyDetail
import com.google.gson.annotations.SerializedName

data class DetailPropertyApiModel(
    @SerializedName("listings")
    val detailData: List<ResultApiModel>? = null // detail data list has only one item
)

fun ResultApiModel.asPropertyDetail() = PropertyDetail(
    id = id.orEmpty(),
    imageUrl = listing?.localization?.de?.attachments?.mapNotNull { it?.url } ?: emptyList(),
    title = listing?.localization?.de?.text?.title.orEmpty(),
    description = listing?.localization?.de?.text?.description.orEmpty(),
    characteristics = listing?.characteristics?.toMapData() ?: emptyMap(),
    price = listing?.prices?.buy?.price ?: 0L,
    currency = listing?.prices?.currency ?: "CHF",
    address = listing?.address?.asAddress().orEmpty(),
    listerBrandingName = listerBranding?.legalName ?: listerBranding?.name,
    listerBrandingLogo = listerBranding?.logoUrl,
    isFavorite = false
)
