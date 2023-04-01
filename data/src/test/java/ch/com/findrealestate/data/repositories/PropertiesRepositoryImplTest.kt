package ch.com.findrealestate.data.repositories

import ch.com.findrealestate.data.datasources.local.LocalDataSource
import ch.com.findrealestate.data.datasources.remote.RemoteDataSource
import ch.com.testutils.MockkUnitTest
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PropertiesRepositoryImplTest : MockkUnitTest() {
    @RelaxedMockK
    lateinit var localDataSource: LocalDataSource

    @RelaxedMockK
    lateinit var remoteDataSource: RemoteDataSource

    private lateinit var repositoryImpl: PropertiesRepositoryImpl

    override fun onCreate() {
        super.onCreate()
        repositoryImpl = PropertiesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `when invoke getProperties then local data source getProperties is called one time`() =
        runTest {
            // When
            repositoryImpl.getProperties()

            // Then
            coVerify(exactly = 1) {
                localDataSource.getProperties()
            }
        }

    @Test
    fun `when invoke refresh then local remote source getProperties() and local data source insertAll() is called one time`() =
        runTest {
            // When
            repositoryImpl.refresh()

            // Then
            coVerify(exactly = 1) {
                remoteDataSource.getProperties()
                localDataSource.insertAll(listOf())
            }
        }

    @Test
    fun `when invoke toggleFavorite then local remote source toggleFavorie() is called one time`() =
        runTest {
            // Given
            val id = "1"
            val isFavorite = true
            val slotId = slot<String>()
            val slotFavorite = slot<Boolean>()

            // When
            repositoryImpl.toggleFavorite(id, isFavorite)

            // Then
            coVerify(exactly = 1) {
                localDataSource.toggleFavorite(
                    id = capture(slotId),
                    isFavorite = capture(slotFavorite)
                )
            }
            assertEquals(id, slotId.captured)
            assertEquals(isFavorite, slotFavorite.captured)
        }
}
