package ch.com.findrealestate.features.home

import ch.com.findrealestate.domain.entity.Property

sealed class HomeItem {
    open val itemId:String = ""
    data class PropertyItem(val property: Property) : HomeItem(){
        override val itemId: String = property.id
    }
    data class SimilarPropertiesItem(val properties: List<Property>) : HomeItem(){
        override val itemId: String = "SimilarPropertyItem"
    }
}
