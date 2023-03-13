package ch.com.findrealestate.data.models
import ch.com.findrealestate.data.mapper.asAddress
import ch.com.findrealestate.domain.entity.Property
import com.google.gson.annotations.SerializedName

data class PropertyApiModel(
    @SerializedName("from")
    val from: Int? = null,
    @SerializedName("maxFrom")
    val maxFrom: Int? = null,
    @SerializedName("results")
    val resultApiModels: List<ResultApiModel>? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("total")
    val total: Int? = null
)

data class ResultApiModel(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("listerBranding")
    val listerBranding: ListerBrandingApiModel? = null,
    @SerializedName("listing")
    val listing: ListingApiModel? = null,
    @SerializedName("listingType")
    val listingType: ListingTypeApiModel? = null,
    @SerializedName("remoteViewing")
    val remoteViewing: Boolean? = null
)

fun ResultApiModel.asProperty() = Property(
    id = id.orEmpty(),
    imageUrl = listing?.localization?.de?.attachments?.firstOrNull()?.url.orEmpty(),
    title = listing?.localization?.de?.text?.title.orEmpty(),
    price = listing?.prices?.buy?.price ?: 0L,
    address = listing?.address?.asAddress().orEmpty(),
    isFavorite = false
)
