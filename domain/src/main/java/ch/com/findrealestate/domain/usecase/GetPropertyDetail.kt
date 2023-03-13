package ch.com.findrealestate.domain.usecase

import ch.com.findrealestate.domain.repositories.PropertiesRepository
import javax.inject.Inject

class GetPropertyDetail @Inject constructor(private val repository: PropertiesRepository) {
    suspend fun invoke(propertyId:String) = repository.getPropertyDetail(propertyId)
}
