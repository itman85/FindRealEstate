package ch.com.findrealestate.features.home.redux

sealed class HomeNavigation {
    object OpenDetailScreen: HomeNavigation()
}
