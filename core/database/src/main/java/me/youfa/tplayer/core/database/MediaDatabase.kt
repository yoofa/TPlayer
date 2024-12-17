package me.youfa.tplayer.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.youfa.tplayer.core.database.dao.DirectoryDao
import me.youfa.tplayer.core.database.dao.MediumDao
import me.youfa.tplayer.core.database.entities.AudioStreamInfoEntity
import me.youfa.tplayer.core.database.entities.DirectoryEntity
import me.youfa.tplayer.core.database.entities.MediumEntity
import me.youfa.tplayer.core.database.entities.SubtitleStreamInfoEntity
import me.youfa.tplayer.core.database.entities.VideoStreamInfoEntity

@Database(
    entities = [
        DirectoryEntity::class,
        MediumEntity::class,
        AudioStreamInfoEntity::class,
        VideoStreamInfoEntity::class,
        SubtitleStreamInfoEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class MediaDatabase : RoomDatabase() {

    abstract fun mediumDao(): MediumDao

    abstract fun directoryDao(): DirectoryDao

    companion object {
        const val DATABASE_NAME = "media_db"
    }
}