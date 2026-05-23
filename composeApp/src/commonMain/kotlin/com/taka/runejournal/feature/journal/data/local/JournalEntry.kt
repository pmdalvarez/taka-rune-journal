package com.taka.runejournal.feature.journal.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
  @PrimaryKey val id: String,
  val text: String,
  val imageFileName: String?,
  val imageCaption: String?,
  val createdAtEpochMillis: Long,
  val updatedAtEpochMillis: Long,
)