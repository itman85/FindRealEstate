package ch.com.findrealestate.data.models

import org.junit.Assert.assertEquals
import org.junit.Test

class GeoCoordinatesApiModelTest {
    @Test
    fun checkCorrectAttributes() {
        val latitude = 0.0
        val geoCoordinatesApiModel = GeoCoordinatesApiModel(
            latitude = latitude,
            longitude = 1.2
        )
        assertEquals(latitude, geoCoordinatesApiModel.latitude)
    }
}
