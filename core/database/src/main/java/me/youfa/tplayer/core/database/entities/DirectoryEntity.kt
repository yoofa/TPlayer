package me.youfa.tplayer.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// copy from next player

@Entity(
    tableName = "directories",
)
data class DirectoryEntity(
    @PrimaryKey @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "parent_path") val parentPath: String? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "last_modified") val modified: Long,

)