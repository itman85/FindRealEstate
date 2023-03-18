package ch.com.findrealestate.features.home.redux

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ch.com.findrealestate.domain.entity.Property

sealed class HomeState {
    open val properties:List<Property> = emptyList()

    object Init : HomeState()
    object Loading : HomeState()
    data class PropertiesLoaded(override val properties: List<Property>) : HomeState()
    data class PropertiesListUpdated(override val properties: List<Property>) : HomeState()
    data class Error(val errorMsg: String) : HomeState()
}
