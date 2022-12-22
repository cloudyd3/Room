package org.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.Dispatchers

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [PersonName::class], version = 1)
abstract class PersonNameRoomDatabase : RoomDatabase() {
    abstract fun personNameDao(): PersonNameDao

    companion object {
        @Volatile
        private var INSTANCE: PersonNameRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PersonNameRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonNameRoomDatabase::class.java,
                    "word_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class RoomDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.personNameDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(personNameDao: PersonNameDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            personNameDao.deleteAll()
            val personName = PersonName(first_name = "Olga", last_name = "Fedorova")
            personNameDao.insert(personName)
        }
    }
}