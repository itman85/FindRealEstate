package ch.com.findrealestate.features.home.redux

import ch.com.findrealestate.domain.entity.Property

// navigation to open other screens, chrome tab, or open stateless dialog, snackbar
interface HomeBaseNavigation
sealed interface HomeNavigation:HomeBaseNavigation {
    data class OpenDetailScreen(val propertyId:String): HomeNavigation

    data class OpenDialogAddFavouriteSuccess( val favoriteProperty: Property): HomeNavigation

    data class OpenDialogRemovedFavouriteConfirmation(val propertyId:String) : HomeNavigation
}
