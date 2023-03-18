package ch.com.findrealestate.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Property(
    val id: String,
    val imageUrl: String,
    val title: String,
    val price: Long,
    val address: String,
    val isFavorite: Boolean
)
