package ch.com.findrealestate.ui.screens

import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import ch.com.findrealestate.domain.usecase.RefreshPropertyUseCase
import ch.com.findrealestate.domain.usecase.FavoriteUseCase
import ch.com.testutils.MockkUnitTest
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest : MockkUnitTest() {
    @RelaxedMockK
    lateinit var getPropertiesUseCase: GetPropertiesUseCase

    @RelaxedMockK
    lateinit var refreshPropertyUseCase: RefreshPropertyUseCase

    @RelaxedMockK
    lateinit var toggleFavoriteUseCase: FavoriteUseCase

    @SpyK
    @InjectMockKs
    private lateinit var homeViewModel: HomeViewModel

    @Test
    fun refreshPropertyUseCase_is_called_one_time() =
        runTest {
            // Assert (Then)
            coVerify(exactly = 1) { refreshPropertyUseCase.invoke() }
        }

    @Test
    fun getPropertiesUseCase_is_called_one_time() =
        runTest {
            // When
            homeViewModel.propertiesUiState
            // Assert (Then)
            verify(exactly = 1) { getPropertiesUseCase.invoke() }
        }

    @Test
    fun toggleFavorite_is_called_one_time_with_proper_parameters() = runTest {
        // Arrange (Given)
        val id = "this id"
        val isFavorite = true
        val slotId = slot<String>()
        val slotFavorite = slot<Boolean>()

        // Act (When)
        homeViewModel.toggleFavorite(id, isFavorite)

        // Assert (Then)
        coVerify(exactly = 1) {
            toggleFavoriteUseCase.invoke(
                capture(slotId),
                capture(slotFavorite)
            )
        }
        assertEquals(id, slotId.captured)
        assertEquals(isFavorite, slotFavorite.captured)
    }
}
