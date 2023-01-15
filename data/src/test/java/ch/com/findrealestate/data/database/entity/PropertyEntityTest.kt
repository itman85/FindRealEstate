package ch.com.findrealestate.data.database.entity

import ch.com.findrealestate.domain.entity.Property
import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyEntityTest {
    @Test
    fun `check correct attributes for property entity`() {
        val id = "test_id"
        val price = 190L
        val isFavorite = true

        val propertyEntity = PropertyEntity(
            id = id,
            imageUrl = "just a test image url",
            title = "title",
            price = price,
            address = "address 123",
            isFavorite = isFavorite
        )

        assertEquals(id, propertyEntity.id)
        assertEquals(price, propertyEntity.price)
        assertEquals(isFavorite, propertyEntity.isFavorite)
    }

    @Test
    fun `map PropertyEntity asProperty`() {
        val expectedProperty = Property(
            id = "id",
            imageUrl = "just a test image url",
            title = "title",
            price = 190L,
            address = "address 123",
            isFavorite = true
        )
        val propertyEntity = PropertyEntity(
            id = "id",
            imageUrl = "just a test image url",
            title = "title",
            price = 190L,
            address = "address 123",
            isFavorite = true
        )
        assertEquals(expectedProperty, propertyEntity.asProperty())
    }

    @Test
    fun `map Property asPropertyEntity`() {
        val expectedPropertyEntity = PropertyEntity(
            id = "id",
            imageUrl = "just a test image url",
            title = "title",
            price = 190L,
            address = "address 123",
            isFavorite = true
        )
        val property = Property(
            id = "id",
            imageUrl = "just a test image url",
            title = "title",
            price = 190L,
            address = "address 123",
            isFavorite = true
        )
        assertEquals(expectedPropertyEntity, property.asPropertyEntity())
    }
}
