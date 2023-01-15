package ch.com.findrealestate.data.datasources.local

import ch.com.findrealestate.data.database.dao.PropertyDao
import ch.com.findrealestate.data.database.entity.asProperty
import ch.com.findrealestate.data.database.entity.asPropertyEntity
import ch.com.findrealestate.domain.entity.Property
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val propertyDao: PropertyDao) {
    fun getProperties(): List<Property> {
        return propertyDao.getAllProperties()
            .map { propertyEntity -> propertyEntity.asProperty() }
    }

    suspend fun insertAll(properties: List<Property>) {
        propertyDao.insertAll(
            properties.map {
                it.asPropertyEntity()
            }
        )
    }

    suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        propertyDao.updateFavorite(id, isFavorite)
    }

    suspend fun checkFavorite(id: String): Boolean {
        return propertyDao.getFavoriteState(id) ?: false
    }
}
