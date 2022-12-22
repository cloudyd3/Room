package org.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonNameDao {
    @Query("SELECT * FROM name_table ORDER BY first_name ASC")
    fun getAlphabetizedNames(): Flow<List<PersonName>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(personName: PersonName)
    @Query("DELETE FROM name_table")
    suspend fun deleteAll()
}
