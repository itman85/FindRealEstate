package ch.com.findrealestate.domain.usecase

import ch.com.findrealestate.domain.repositories.PropertiesRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor (private val propertiesRepository: PropertiesRepository) {
    suspend fun invoke(id: String, isFavorite: Boolean) = propertiesRepository.toggleFavorite(id, isFavorite)
}
