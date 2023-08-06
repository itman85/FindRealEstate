package ch.com.findrealestate.mock

interface PropertiesApiMocker {

    fun getPropertiesSuccess()

    fun getPropertiesFailed()

    fun getSimilarPropertiesSuccess()

    fun getDetailPropertySuccess(propertyId: String)

    fun getDetailPropertyFailed(propertyId: String)
}
