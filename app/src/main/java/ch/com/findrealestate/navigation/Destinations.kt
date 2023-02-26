package ch.com.findrealestate.navigation

sealed class Destinations(val route: String) {
    object HomeScreen : Destinations("home")
    data class DetailScreen(val detailData: String = "detailData") : Destinations("detail")
}
