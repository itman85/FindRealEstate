package ch.com.findrealestate.mock

import ch.com.findrealestate.data.models.DetailPropertyApiModel
import ch.com.findrealestate.data.models.PropertyApiModel

interface PropertiesApiMocker {

    fun getPropertiesSuccess()

    fun getDetailPropertySuccess(propertyId:String)
}
