package ch.com.findrealestate.features.detail.redux

sealed class DetailNavigation{

    object NoNavigation: DetailNavigation()
    data class OpenPropertyWebsite(val url:String):DetailNavigation()
}
