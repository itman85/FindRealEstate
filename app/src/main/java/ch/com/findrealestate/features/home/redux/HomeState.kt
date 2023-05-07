package ch.com.findrealestate.features.home.redux

import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.features.home.HomeItem

sealed class HomeState(currentState: HomeState?) {
    open val items: List<HomeItem> = currentState?.items ?: emptyList()

    object Init : HomeState(null)

    class Loading(currentState: HomeState) : HomeState(currentState)
    class PropertiesLoaded(currentState: HomeState, override val items: List<HomeItem>) :
        HomeState(currentState)

    class PropertiesListUpdated(currentState: HomeState, override val items: List<HomeItem>) :
        HomeState(currentState)

    class Error(currentState: HomeState, val errorMsg: String) : HomeState(currentState)

    class AddFavoriteSuccessful(
        currentState: HomeState,
        override val items: List<HomeItem>,
        val favoriteProperty: Property
    ) : HomeState(currentState)

    class ConfirmFavoriteRemoved(
        currentState: HomeState,
        val propertyId:String
    ) : HomeState(currentState)
}
