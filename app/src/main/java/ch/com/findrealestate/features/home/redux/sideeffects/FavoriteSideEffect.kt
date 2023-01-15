package ch.com.findrealestate.features.home.redux.sideeffects

import ch.com.findrealestate.domain.usecase.FavoriteUseCase
import ch.com.findrealestate.features.base.ofType
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeState
import com.freeletics.flowredux.GetState
import com.freeletics.flowredux.SideEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteSideEffect @Inject constructor(private val favoriteUseCase:FavoriteUseCase) :SideEffect<HomeState, HomeAction> {
    override fun invoke(
        actions: Flow<HomeAction>,
        getState: GetState<HomeState>
    ): Flow<HomeAction> {
        return actions.ofType(HomeAction.FavoriteClick::class)
            .mapLatest {
                val isFavorite = favoriteUseCase.checkFavorite(it.propertyId)
                favoriteUseCase.toggleFavorite(it.propertyId,!isFavorite)
                // return action after favorite updated
                HomeAction.FavoriteUpdated(it.propertyId,!isFavorite)
            }
    }
}
