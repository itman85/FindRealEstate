package ch.com.findrealestate.domain.usecase

import ch.com.findrealestate.domain.repositories.PropertiesRepository
import javax.inject.Inject

class FavoriteUseCase @Inject constructor (private val propertiesRepository: PropertiesRepository) {
    suspend fun toggleFavorite(id: String, isFavorite: Boolean) = propertiesRepository.toggleFavorite(id, isFavorite)

    suspend fun checkFavorite(id: String) = propertiesRepository.checkFavorite(id)
}
