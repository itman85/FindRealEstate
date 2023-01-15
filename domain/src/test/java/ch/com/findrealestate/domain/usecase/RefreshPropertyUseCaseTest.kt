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
class RefreshPropertyUseCaseTest : MockkUnitTest() {
    @RelaxedMockK
    lateinit var propertyRepository: PropertiesRepository

    @SpyK
    @InjectMockKs
    private lateinit var refreshPropertyUseCase: RefreshPropertyUseCase

    @Test
    fun `when invoke refreshPropertyUseCase then PropertiesRepository refresh is called one time`() =
        runTest {
            // Act (When)
            refreshPropertyUseCase.invoke()

            // Assert (Then)
            coVerify(exactly = 1) { propertyRepository.refresh() }
        }
}
