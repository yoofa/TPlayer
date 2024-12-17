package me.youfa.tplayer.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// copy from next player

@Entity(
    tableName = "media",
    indices = [
        Index(value = ["uri"], unique = true),
        Index(value = ["path"], unique = true),
    ],
)
data class MediumEntity(
    @PrimaryKey @ColumnInfo(name = "uri") val uri: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "parent_path") val parentPath: String? = null,
    @ColumnInfo(name = "last_modified") val modified: Long,


    @ColumnInfo(name = "size") val size: Long,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "media_store_id") val mediaStoreId: Long,

    // Medium info
    @ColumnInfo(name = "format") val format: String? = null,
    @ColumnInfo(name = "thumbnail_path") val thumbnailPath: String? = null,

    // Medium playback state
    @ColumnInfo(name = "playback_position") val playbackPosition: Long = 0,
    @ColumnInfo(name = "audio_track_index") val audioTrackIndex: Int? = null,
    @ColumnInfo(name = "video_track_index") val videoTrackIndex: Int? = null,
    @ColumnInfo(name = "subtitle_track_index") val subtitleTrackIndex: Int? = null,
    @ColumnInfo(name = "playback_speed") val playbackSpeed: Float? = null,
    @ColumnInfo(name = "last_played_time") val lastPlayedTime: Long? = null,
    @ColumnInfo(name = "external_subs") val externalSubs: String = "",
    @ColumnInfo(name = "video_scale") val videoScale: Float = 1f,
)