package ch.com.findrealestate.data.datasources.remote

import ch.com.findrealestate.data.models.PropertyApiModel
import ch.com.findrealestate.data.models.ResultApiModel
import ch.com.findrealestate.domain.entity.Property
import ch.com.testutils.MockkUnitTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceImplTest : MockkUnitTest() {
    @RelaxedMockK
    lateinit var propertiesApi: PropertiesApi

    private lateinit var remoteDataSourceImpl: RemoteDataSource

    override fun onCreate() {
        super.onCreate()
        remoteDataSourceImpl = RemoteDataSource(propertiesApi)
    }

    @Test
    fun `when invoke getProperties then api getProperties() is called one time`() = runTest {
        // When
        remoteDataSourceImpl.getProperties()

        // Then
        coVerify(exactly = 1) {
            propertiesApi.getProperties()
        }
    }

    @Test
    fun `verity result with mock return from the api `() = runTest {
        // Given
        coEvery { propertiesApi.getProperties() } returns PropertyApiModel(
            resultApiModels = listOf(
                ResultApiModel()
            )
        )

        // When
        val result = remoteDataSourceImpl.getProperties()

        // Then
        assertEquals(
            listOf(
                Property(
                    id = "",
                    imageUrl = "",
                    title = "",
                    price = 0,
                    address = "",
                    isFavorite = false
                )
            ),
            result
        )
    }
}
