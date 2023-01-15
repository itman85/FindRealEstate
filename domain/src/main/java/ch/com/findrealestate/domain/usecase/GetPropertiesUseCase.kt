package ch.com.findrealestate.domain.usecase

import ch.com.findrealestate.domain.repositories.PropertiesRepository
import javax.inject.Inject

class GetPropertiesUseCase @Inject constructor (private val propertiesRepository: PropertiesRepository) {
    suspend fun invoke() = propertiesRepository.getProperties()
}
