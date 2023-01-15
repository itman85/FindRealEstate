package ch.com.findrealestate.data.mapper

import ch.com.findrealestate.data.models.AddressXApiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AddressXApiModelMapperTest {

    private lateinit var addressXApiModel: AddressXApiModel

    @Before
    fun setup() {
        addressXApiModel = AddressXApiModel(
            country = "country",
            geoCoordinates = null,
            locality = "locality",
            postalCode = "postalCode",
            region = "region",
            street = "street"
        )
    }

    @Test
    fun `map to simple address`() {
        val expected = "street, region, country"
        assertEquals(expected, addressXApiModel.asAddress())
    }
}
