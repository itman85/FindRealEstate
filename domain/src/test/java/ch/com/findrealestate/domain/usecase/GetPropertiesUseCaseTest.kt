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
class GetPropertiesUseCaseTest : MockkUnitTest() {
    @RelaxedMockK
    lateinit var propertyRepository: PropertiesRepository

    @SpyK
    @InjectMockKs
    private lateinit var getPropertiesUseCase: GetPropertiesUseCase

    @Test
    fun `when invoke getPropertiesUseCase then PropertiesRepository getProperties is called one time`() =
        runTest {
            // Act (When)
            getPropertiesUseCase.invoke()

            // Assert (Then)
            coVerify(exactly = 1) { propertyRepository.getProperties() }
        }
}
