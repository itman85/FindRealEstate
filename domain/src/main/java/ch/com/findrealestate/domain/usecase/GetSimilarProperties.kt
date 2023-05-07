package ch.com.findrealestate.domain.usecase

import ch.com.findrealestate.domain.repositories.PropertiesRepository
import javax.inject.Inject

class GetSimilarProperties @Inject constructor (private val propertiesRepository: PropertiesRepository) {

    suspend fun invoke() = propertiesRepository.getProperties().takeLast(5)

}
