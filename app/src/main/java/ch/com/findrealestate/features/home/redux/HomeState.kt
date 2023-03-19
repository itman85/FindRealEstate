package ch.com.findrealestate.features.home.redux

import ch.com.findrealestate.domain.entity.Property

sealed class HomeState(currentState: HomeState?) {
    open val properties: List<Property> = currentState?.properties ?: emptyList()

    object Init : HomeState(null)

    class Loading(currentState: HomeState) : HomeState(currentState)
    class PropertiesLoaded(currentState: HomeState, override val properties: List<Property>) :
        HomeState(currentState)

    class PropertiesListUpdated(currentState: HomeState, override val properties: List<Property>) :
        HomeState(currentState)

    class Error(currentState: HomeState, val errorMsg: String) : HomeState(currentState)

    class AddFavoriteSuccessful(
        currentState: HomeState,
        override val properties: List<Property>,
        val favoriteProperty: Property
    ) : HomeState(currentState)

    class ConfirmFavoriteRemoved(
        currentState: HomeState,
        val propertyId:String
    ) : HomeState(currentState)
}
