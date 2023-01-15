package ch.com.findrealestate.data.repositories

import ch.com.findrealestate.data.datasources.local.LocalDataSource
import ch.com.findrealestate.data.datasources.remote.RemoteDataSource
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.repositories.PropertiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PropertiesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : PropertiesRepository {
    override suspend fun getProperties(): List<Property> {
        return remoteDataSource.getProperties()
    }

    override suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val properties = remoteDataSource.getProperties()
            localDataSource.insertAll(properties)
        }
    }

    override suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            localDataSource.toggleFavorite(id, isFavorite)
        }
    }
}
