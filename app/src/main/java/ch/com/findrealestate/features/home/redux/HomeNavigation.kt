package ch.com.findrealestate.features.home.redux

sealed class HomeNavigation {

    data class OpenDetailScreen(val propertyId:String): HomeNavigation()
}
