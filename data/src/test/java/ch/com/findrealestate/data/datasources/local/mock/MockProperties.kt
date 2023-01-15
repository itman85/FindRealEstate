package ch.com.findrealestate.data.datasources.local.mock

import ch.com.findrealestate.domain.entity.Property

object MockProperties {
    fun getMockProperties(): List<Property> = listOf(
        Property(
            id = "id _ 1",
            imageUrl = "imageUrl 1",
            title = "title 1",
            price = 90L,
            address = "Ha noi",
            isFavorite = true
        ),
        Property(
            id = "id _ 2",
            imageUrl = "imageUrl 2",
            title = "title",
            price = 90L,
            address = "Ho chi minh city",
            isFavorite = true
        )
    )
}
