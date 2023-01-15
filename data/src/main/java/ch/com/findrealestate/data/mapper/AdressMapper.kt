package ch.com.findrealestate.data.mapper

import ch.com.findrealestate.data.models.AddressXApiModel

fun AddressXApiModel.asAddress() = "$street, $region, $country"
