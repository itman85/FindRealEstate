package ch.com.findrealestate.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.com.findrealestate.data.database.Constants.DATABASE_NAME
import ch.com.findrealestate.data.database.dao.PropertyDao
import ch.com.findrealestate.data.database.entity.PropertyEntity

@Database(
    entities = [PropertyEntity::class],
    version = 2
)
abstract class PropertyDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao

    companion object {
        @Volatile
        private var instance: PropertyDatabase? = null
        fun getInstance(context: Context): PropertyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PropertyDatabase {
            return Room.databaseBuilder(context, PropertyDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
