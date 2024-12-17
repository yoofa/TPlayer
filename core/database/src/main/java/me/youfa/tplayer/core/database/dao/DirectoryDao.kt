package me.youfa.tplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.youfa.tplayer.core.database.entities.DirectoryEntity
import me.youfa.tplayer.core.database.relations.DirectoryWithMedia

// copy from next player

@Dao
interface DirectoryDao {
    @Upsert
    suspend fun upsert(directory: DirectoryEntity)

    @Upsert
    suspend fun upsertAll(directories: List<DirectoryEntity>)

    @Query("SELECT * FROM directories")
    fun getAll(): Flow<List<DirectoryEntity>>

    @Transaction
    @Query("SELECT * FROM directories")
    fun getAllWithMedia(): Flow<List<DirectoryWithMedia>>

    @Query("DELETE FROM directories WHERE path in (:paths)")
    suspend fun delete(paths: List<String>)
}