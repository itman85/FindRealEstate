package ch.com.findrealestate.features.home.redux

import ch.com.findrealestate.domain.entity.Property

sealed interface HomeAction {
    object StartLoadData : HomeAction
    data class FavoriteClick(val propertyId: String) : HomeAction
    data class FavoriteUpdated(val propertyId: String, val isFavorite: Boolean) : HomeAction
    data class DataLoaded(val properties: List<Property>) : HomeAction
    data class DataLoadedError(val error: String?) : HomeAction
}
