package ch.com.findrealestate.features.detail.redux

import ch.com.findrealestate.domain.entity.PropertyDetail

data class DetailState(
    val propertyId: String? = null,
    val detailProperty: PropertyDetail? = null,
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val isShowInfoBottomSheet: Boolean = false
){
    companion object{
        val Init = DetailState()
    }
    fun isLoadingState() = isLoading
    fun isDataLoaded() = detailProperty!=null
    fun isErrorState() = !errorMsg.isNullOrEmpty()
}
