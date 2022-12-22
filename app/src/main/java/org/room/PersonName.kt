package org.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "name_table", primaryKeys = ["first_name", "last_name"])
data class PersonName(
    val first_name: String,
    val last_name: String
)


