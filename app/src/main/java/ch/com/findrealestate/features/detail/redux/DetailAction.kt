package ch.com.findrealestate.features.detail.redux

import ch.com.findrealestate.domain.entity.PropertyDetail

sealed interface DetailAction {

    data class LoadDetailData(val propertyId: String) : DetailAction

    data class DetailDataLoaded(val data: PropertyDetail) : DetailAction

    object DetailDataLoading : DetailAction

    data class DetailDataLoadedError(val errorMsg: String) : DetailAction

    object OpenPropertyWebsiteClick : DetailAction

    data class ScreenResumed(val lastNavigation: DetailNavigation?) : DetailAction

    data class ToggleShowPropertyInfoBottomSheet(val isShow:Boolean) : DetailAction
}
