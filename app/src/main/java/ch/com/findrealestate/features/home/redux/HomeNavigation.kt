package ch.com.findrealestate.features.home.redux

interface HomeBaseNavigation
sealed interface HomeNavigation:HomeBaseNavigation {
    data class OpenDetailScreen(val propertyId:String): HomeNavigation
}
