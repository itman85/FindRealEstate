package ch.com.findrealestate.features.detail.redux

import ch.com.findrealestate.features.detail.DetailNavigator

sealed class DetailNavigation{

    object NoNavigation: DetailNavigation()
    data class OpenPropertyWebsite(val url:String):DetailNavigation()
}
