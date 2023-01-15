package ch.com.findrealestate.data.datasources.local

import ch.com.findrealestate.data.database.dao.PropertyDao
import ch.com.findrealestate.data.database.entity.PropertyEntity
import ch.com.findrealestate.data.database.entity.asPropertyEntity
import ch.com.findrealestate.data.datasources.local.mock.MockProperties
import ch.com.testutils.MockkUnitTest
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSourceImplTest : MockkUnitTest() {
    @RelaxedMockK
    lateinit var propertyDao: PropertyDao

    private lateinit var localDataSourceImpl: LocalDataSource

    override fun onCreate() {
        super.onCreate()
        localDataSourceImpl = LocalDataSource(propertyDao)
    }

    @Test
    fun `when invoke getProperties then dao getProperties() is called one time`() = runTest {
        // When
        localDataSourceImpl.getProperties()

        // Then
        coVerify(exactly = 1) {
            propertyDao.getAllProperties()
        }
    }

    @Test
    fun `when invoke insertAll then dao insertAll() is called one time`() =
        runTest {
            // Given
            val properties = MockProperties.getMockProperties()
            val slotProperties = slot<List<PropertyEntity>>()
            val expectedPropertyEntities = properties.map {
                it.asPropertyEntity()
            }

            // When
            localDataSourceImpl.insertAll(properties)

            // Then
            coVerify(exactly = 1) {
                propertyDao.insertAll(
                    propertyEntities = capture(slotProperties)
                )
            }

            assertEquals(expectedPropertyEntities, slotProperties.captured)
        }

    @Test
    fun `when invoke toggleFavorite then dao toggleFavorie() is called one time`() =
        runTest {
            // Given
            val id = "1"
            val isFavorite = true
            val slotId = slot<String>()
            val slotFavorite = slot<Boolean>()

            // When
            localDataSourceImpl.toggleFavorite(id, isFavorite)

            // Then
            coVerify(exactly = 1) {
                propertyDao.updateFavorite(
                    id = capture(slotId),
                    isFavorite = capture(slotFavorite)
                )
            }
            assertEquals(id, slotId.captured)
            assertEquals(isFavorite, slotFavorite.captured)
        }
}
