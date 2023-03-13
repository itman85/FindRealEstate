package ch.com.findrealestate.data.datasources.remote

import ch.com.findrealestate.data.models.asProperty
import ch.com.findrealestate.data.models.asPropertyDetail
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.entity.PropertyDetail
import javax.inject.Inject

class RemoteDataSource @Inject constructor (private val propertiesApi: PropertiesApi) {
    suspend fun getProperties(): List<Property> {
        return propertiesApi.getProperties().resultApiModels?.map { it.asProperty() }.orEmpty()
    }
    suspend fun getPropertyDetail(id:String):PropertyDetail?{
        return propertiesApi.getDetailProperty(id).detailData?.firstOrNull()?.asPropertyDetail()
    }
}
