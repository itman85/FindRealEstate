package ch.com.findrealestate.domain.repositories

import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.entity.PropertyDetail

interface PropertiesRepository {
    suspend fun getProperties(): List<Property>

    suspend fun getPropertyDetail(propertyId:String):PropertyDetail?

    suspend fun refresh()

    suspend fun toggleFavorite(id: String, isFavorite: Boolean)

    suspend fun checkFavorite(id: String): Boolean
}
