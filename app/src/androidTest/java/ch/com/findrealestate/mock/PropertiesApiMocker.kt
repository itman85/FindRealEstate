package ch.com.findrealestate.mock

interface PropertiesApiMocker : ApiMocker {

    fun getPropertiesSuccess(delayResponseInSecond: Long = 3L)

    fun getPropertiesFailed()

    fun getSimilarPropertiesSuccess()

    fun getDetailPropertySuccess(propertyId: String)

    fun getDetailPropertyFailed(propertyId: String)
}
