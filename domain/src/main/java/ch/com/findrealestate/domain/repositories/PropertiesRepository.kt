package ch.com.findrealestate.domain.repositories

import ch.com.findrealestate.domain.entity.Property

interface PropertiesRepository {
    suspend fun getProperties(): List<Property>

    suspend fun refresh()

    suspend fun toggleFavorite(id: String, isFavorite: Boolean)
}
