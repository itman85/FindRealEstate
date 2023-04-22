package ch.com.findrealestate.features.home.components.similarproperties.redux

import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.features.home.redux.HomeBaseAction

sealed interface HomeSimilarPropertiesAction : HomeBaseAction {

    data class SimilarPropertiesLoaded(val properties: List<Property>) : HomeSimilarPropertiesAction

    data class SimilarPropertiesLoadedError(val error: String?) : HomeSimilarPropertiesAction

    data class SimilarPropertyClick(val propertyId: String) : HomeSimilarPropertiesAction
}
