package me.youfa.tplayer.core.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import me.youfa.tplayer.core.database.entities.AudioStreamInfoEntity
import me.youfa.tplayer.core.database.entities.MediumEntity
import me.youfa.tplayer.core.database.entities.SubtitleStreamInfoEntity
import me.youfa.tplayer.core.database.entities.VideoStreamInfoEntity

data class MediumWithInfo(
    @Embedded val mediumEntity: MediumEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val audioStreamsInfo: List<AudioStreamInfoEntity>,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val videoStreamInfo: List<VideoStreamInfoEntity>,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val subtitleStreamsInfo: List<SubtitleStreamInfoEntity>,
)