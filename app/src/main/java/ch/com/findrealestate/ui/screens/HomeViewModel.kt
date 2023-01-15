package ch.com.findrealestate.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import ch.com.findrealestate.domain.usecase.RefreshPropertyUseCase
import ch.com.findrealestate.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPropertiesUseCase: GetPropertiesUseCase,
    private val refreshPropertyUseCase: RefreshPropertyUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            try {
                refreshPropertyUseCase.invoke()
            } catch (exception: Exception) {
                // todo: update ui state
            }
        }
    }
    /*
    val propertiesUiState: StateFlow<HomeUiState> = getProperties().asResult().map {
        result ->
        when (result) {
            is Result.Success -> HomeUiState.Success(result.data)
            is Result.Error -> HomeUiState.Error
            is Result.Loading -> HomeUiState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = HomeUiState.Loading
    )
     */

    private suspend fun getProperties() = getPropertiesUseCase.invoke()

    fun toggleFavorite(id: String, favorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase.invoke(id, favorite)
        }
    }
}

sealed interface HomeUiState {
    data class Success(val properties: List<Property>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}
