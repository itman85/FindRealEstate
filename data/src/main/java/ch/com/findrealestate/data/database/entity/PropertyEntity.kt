package ch.com.findrealestate.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ch.com.findrealestate.domain.entity.Property

@Entity(tableName = "property")
data class PropertyEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "price") val price: Long,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "isFavorite", defaultValue = "false") val isFavorite: Boolean
)

fun PropertyEntity.asProperty() = Property(
    id = id,
    imageUrl = imageUrl,
    title = title,
    price = price,
    address = address,
    isFavorite = isFavorite
)

fun Property.asPropertyEntity() = PropertyEntity(
    id = id,
    imageUrl = imageUrl,
    title = title,
    price = price,
    address = address,
    isFavorite = isFavorite
)
