package ch.com.findrealestate.features.home

import android.util.Log
import ch.com.findrealestate.base.FlowReduxViewModel
import ch.com.findrealestate.features.home.components.similarproperties.redux.HomeSimilarPropertiesNavigation
import ch.com.findrealestate.features.home.redux.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeStateViewModel @Inject constructor(
    stateMachine: HomeStateMachine
) : FlowReduxViewModel<HomeState, HomeBaseAction, HomeBaseNavigation>(stateMachine) {

    private lateinit var navigator: HomeNavigator
    fun setNavigator(navigator: HomeNavigator) {
        this.navigator = navigator
    }

    override fun handleNavigation(navigation: HomeBaseNavigation) {
        // this helps to know if navigator is not initialized
        if (!this::navigator.isInitialized) {
            error("HomeNavigator is not initialized")
        }
        Log.d("Phan2", "navigation change $navigation")
        when (navigation) {
            is HomeNavigation.OpenDetailScreen -> navigator.navigateToDetail(navigation.propertyId)
            is HomeSimilarPropertiesNavigation.OpenSimilarPropertyDetail -> navigator.navigateToDetail(
                navigation.propertyId
            )
            else -> {
                Log.i("Phan2", "No navigation, just stay Home screen")
            }
        }
    }
}

