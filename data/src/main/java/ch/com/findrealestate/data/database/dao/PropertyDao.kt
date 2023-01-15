package ch.com.findrealestate.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ch.com.findrealestate.data.database.entity.PropertyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {
    @Query(value = """SELECT * FROM property""")
    fun getAllProperties(): List<PropertyEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(propertyEntities: List<PropertyEntity>) {
        val updatedProperties = propertyEntities.map {
            it.copy(isFavorite = getFavoriteState(it.id) ?: false)
        }
        insertAllNew(updatedProperties)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNew(propertyEntities: List<PropertyEntity>)

    @Query(value = """SELECT isFavorite FROM property WHERE id =:id""")
    suspend fun getFavoriteState(id: String): Boolean?

    @Query("UPDATE property SET isFavorite = :isFavorite WHERE id =:id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)
}
