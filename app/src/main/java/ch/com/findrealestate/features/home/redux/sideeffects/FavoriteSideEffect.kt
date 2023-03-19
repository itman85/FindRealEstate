package ch.com.findrealestate.features.home.redux.sideeffects

import ch.com.findrealestate.domain.usecase.FavoriteUseCase
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeState
import com.freeletics.flowredux.GetState
import com.freeletics.flowredux.SideEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteSideEffect @Inject constructor(private val favoriteUseCase: FavoriteUseCase) :
    SideEffect<HomeState, HomeAction> {
    override fun invoke(
        actions: Flow<HomeAction>,
        getState: GetState<HomeState>
    ): Flow<HomeAction> {
        return actions.filter { it is HomeAction.FavoriteClick || it is HomeAction.ConfirmRemoveFavoriteYesClick }
            .mapLatest { action ->
                val propertyId = if (action is HomeAction.FavoriteClick) action.propertyId else (action as HomeAction.ConfirmRemoveFavoriteYesClick).propertyId
                if(action is HomeAction.FavoriteClick) {
                    val isFavorite = favoriteUseCase.checkFavorite(propertyId)
                    if (!isFavorite) {
                        favoriteUseCase.toggleFavorite(propertyId, !isFavorite)
                        // return action after favorite updated
                        HomeAction.FavoriteUpdated(propertyId, !isFavorite)
                    } else {
                        HomeAction.ConfirmRemoveFavorite(propertyId)
                    }
                }else{
                    // ConfirmRemoveFavoriteYesClick
                    favoriteUseCase.toggleFavorite(propertyId, false)
                    // return action after favorite updated
                    HomeAction.FavoriteUpdated(propertyId, false)
                }
            }
    }
}
