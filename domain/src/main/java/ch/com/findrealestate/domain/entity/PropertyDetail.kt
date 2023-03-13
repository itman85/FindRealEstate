package ch.com.findrealestate.domain.entity

data class PropertyDetail(
    val id: String,
    val imageUrl: List<String>,
    val title: String,
    val description: String,
    val characteristics:Map<String,String>,
    val price: Long,
    val currency: String,
    val address: String,
    val listerBrandingName:String?,
    val listerBrandingLogo:String?,
    val isFavorite: Boolean
)
