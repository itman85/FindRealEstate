package ch.com.findrealestate.redux.home

import ch.com.findrealestate.domain.entity.Property

sealed interface HomeState {
    object Init: HomeState
    object Loading: HomeState
    data class PropertiesLoaded(val properties: List<Property>): HomeState
    data class Error(val errorMsg:String): HomeState
}
