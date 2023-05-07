package ch.com.findrealestate.features.home.redux

import ch.com.findrealestate.domain.entity.Property

interface HomeBaseAction
sealed interface HomeAction : HomeBaseAction {
    object StartLoadData : HomeAction
    data class FavoriteClick(val propertyId: String) : HomeAction
    data class FavoriteUpdated(val propertyId: String, val isFavorite: Boolean) : HomeAction
    data class ConfirmRemoveFavorite(val propertyId: String) : HomeAction
    data class DataLoaded(val properties: List<Property>) : HomeAction
    data class DataLoadedError(val error: String?) : HomeAction
    data class PropertyClick(val propertyId: String) : HomeAction

    object FavoriteDialogYesClick : HomeAction

    data class ConfirmRemoveFavoriteYesClick(val propertyId: String) : HomeAction

    object ConfirmRemoveFavoriteNoClick : HomeAction
}
