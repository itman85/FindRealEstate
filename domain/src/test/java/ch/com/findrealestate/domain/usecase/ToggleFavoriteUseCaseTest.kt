package ch.com.findrealestate.domain.usecase

import ch.com.findrealestate.domain.repositories.PropertiesRepository
import ch.com.testutils.MockkUnitTest
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ToggleFavoriteUseCaseTest : MockkUnitTest() {
    @RelaxedMockK
    lateinit var propertyRepository: PropertiesRepository

    @SpyK
    @InjectMockKs
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @Test
    fun `when invoke toggleFavoriteUseCase then PropertiesRepository toggle favorite is called one time`() =
        runTest {
            // Arrange (Given)
            val id = "this id"
            val isFavorite = true

            // Act (When)
            toggleFavoriteUseCase.invoke(id, isFavorite)

            // Assert (Then)
            coVerify(exactly = 1) { propertyRepository.toggleFavorite(id, isFavorite) }
        }
}
