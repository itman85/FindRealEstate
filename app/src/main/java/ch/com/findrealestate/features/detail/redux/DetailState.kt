package ch.com.findrealestate.features.detail.redux

import ch.com.findrealestate.domain.entity.PropertyDetail

sealed class DetailState {
    object Init : DetailState()
    data class DetailDataLoaded(val propertyDetail: PropertyDetail) : DetailState()
    object DetailDataLoading : DetailState()
    data class DetailDataLoadedError(val errorMsg: String) : DetailState()
}
