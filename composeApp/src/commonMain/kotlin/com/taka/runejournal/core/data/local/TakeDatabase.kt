package com.taka.runejournal.core.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.taka.runejournal.feature.timeline.data.local.PpfRuneReadingEntity
import com.taka.runejournal.feature.timeline.data.local.SingleRuneReadingEntity
import com.taka.runejournal.feature.timeline.data.local.TimelineItemDao
import com.taka.runejournal.feature.timeline.data.local.TimelineItemEntity

@Database(
  entities = [
    TimelineItemEntity::class,
    SingleRuneReadingEntity::class,
    PpfRuneReadingEntity::class,
  ],
  version = 1,
)
abstract class TakaDatabase : RoomDatabase() {

  abstract fun timelineItemDao(): TimelineItemDao

}