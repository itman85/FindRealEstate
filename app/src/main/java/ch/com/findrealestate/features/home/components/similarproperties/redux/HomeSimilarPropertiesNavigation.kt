package ch.com.findrealestate.features.home.components.similarproperties.redux

import ch.com.findrealestate.features.home.redux.HomeBaseNavigation

sealed interface HomeSimilarPropertiesNavigation : HomeBaseNavigation {
    data class OpenSimilarPropertyDetail(val propertyId: String) : HomeSimilarPropertiesNavigation
}
