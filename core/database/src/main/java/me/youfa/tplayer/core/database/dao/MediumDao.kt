package me.youfa.tplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.youfa.tplayer.core.database.entities.AudioStreamInfoEntity
import me.youfa.tplayer.core.database.entities.MediumEntity
import me.youfa.tplayer.core.database.entities.SubtitleStreamInfoEntity
import me.youfa.tplayer.core.database.entities.VideoStreamInfoEntity
import me.youfa.tplayer.core.database.relations.MediumWithInfo

// copy from next player

@Dao
interface MediumDao {
    @Upsert
    suspend fun upsert(medium: MediumEntity)

    @Upsert
    suspend fun upsertAll(media: List<MediumEntity>)

    @Query("SELECT * FROM media WHERE uri = :uri")
    fun get(uri: String): MediumEntity?

    @Query("SELECT * FROM media")
    fun getAll(): Flow<List<MediumEntity>>

    @Query("SELECT * FROM media WHERE parent_path = :directoryPath")
    fun getAllFromDirectory(directoryPath: String): Flow<List<MediumEntity>>

    @Transaction
    @Query("SELECT * FROM media WHERE uri = :uri")
    fun getWithInfo(uri: String): MediumWithInfo?

    @Query("SELECT * FROM media")
    fun getAllWithInfo(): Flow<List<MediumWithInfo>>

    @Query("SELECT * FROM media WHERE parent_path = :directoryPath")
    fun getAllWithInfoFromDirectory(directoryPath: String): Flow<List<MediumWithInfo>>

    @Query("DELETE FROM media WHERE uri in (:uris)")
    suspend fun delete(uris: List<String>)

    @Query(
        "UPDATE OR REPLACE media SET " + "playback_position = :position, " + "audio_track_index = :audioTrackIndex, " + "video_track_index = :videoTrackIndex, " + "subtitle_track_index = :subtitleTrackIndex, " + "playback_speed = :playbackSpeed, " + "external_subs = :externalSubs, " + "last_played_time = :lastPlayedTime, " + "video_scale = :videoScale " + "WHERE uri = :uri",
    )
    suspend fun updatePlaybackState(
        uri: String,
        position: Long,
        audioTrackIndex: Int,
        videoTrackIndex: Int,
        subtitleTrackIndex: Int,
        playbackSpeed: Float,
        externalSubs: String,
        lastPlayedTime: Long,
        videoScale: Float
    )

    @Upsert
    suspend fun upsertAudioStreamInfo(audioStreamInfoEntity: AudioStreamInfoEntity)

    @Upsert
    suspend fun upsertVideoStreamInfo(videoStreamInfoEntity: VideoStreamInfoEntity)

    @Upsert
    suspend fun upsertSubtitleStreamInfo(subtitleStreamInfoEntity: SubtitleStreamInfoEntity)
}