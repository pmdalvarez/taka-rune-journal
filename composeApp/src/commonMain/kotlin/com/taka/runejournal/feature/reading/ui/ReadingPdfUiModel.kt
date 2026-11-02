package com.taka.runejournal.feature.reading.ui

import com.taka.runejournal.core.domain.model.DrawnRune
import com.taka.runejournal.core.domain.model.ReadingTopic
import com.taka.runejournal.core.domain.model.Rune
import com.taka.runejournal.core.domain.model.RuneOrientation
import com.taka.runejournal.core.domain.model.RuneSpread
import com.taka.runejournal.core.ui.generalInterpretation
import com.taka.runejournal.core.ui.generalKeywords
import com.taka.runejournal.core.ui.supplementalInterpretation
import com.taka.runejournal.core.ui.supplementalKeywords
import com.taka.runejournal.core.ui.utils.formatAbsolute
import com.taka.runejournal.feature.reading.domain.model.ReadingQrPayload
import com.taka.runejournal.feature.reading.domain.model.toReadingQrPayload
import com.taka.runejournal.feature.timeline.domain.model.TimelineItem
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.reading_position_future_description
import taka_rune_journal.composeapp.generated.resources.reading_position_past_description
import taka_rune_journal.composeapp.generated.resources.reading_position_present_description
import taka_rune_journal.composeapp.generated.resources.reading_tab_future_rune
import taka_rune_journal.composeapp.generated.resources.reading_tab_past_rune
import taka_rune_journal.composeapp.generated.resources.reading_tab_present_rune

data class ReadingPdfDrawnRuneUiModel(
  val rune: Rune,
  val orientation: RuneOrientation,
  val position: StringResource?,
  val positionDescription: StringResource?,
  val interpretation: StringResource,
  val supplementalInterpretation: StringResource?,
  val keywords: StringResource,
  val supplementalKeywords: StringResource?
)

data class ReadingPdfUiModel(
  val id: Long,
  val createdAt: String,
  val spread: RuneSpread,
  val topic: ReadingTopic,
  val reader: String,
  val recipient: String,
  val question: String?,
  val qrPayload: ReadingQrPayload,
  val runes: List<ReadingPdfDrawnRuneUiModel>,
)

fun TimelineItem.SingleRuneReading.toReadingPdfUiModel(reader: String): ReadingPdfUiModel =
  ReadingPdfUiModel(
    id = id,
    createdAt = createdAt.formatAbsolute(),
    spread = RuneSpread.SINGLE_RUNE,
    topic = topic,
    reader = reader,
    recipient = recipient ?: "",
    question = question,
    qrPayload = toReadingQrPayload(),
    runes = listOf(
      drawnRune.toReadingPdfDrawnRuneUiModel(topic = topic),
    ),
  )

fun TimelineItem.PpfRuneReading.toReadingPdfUiModel(reader: String): ReadingPdfUiModel =
  ReadingPdfUiModel(
    id = id,
    createdAt = createdAt.formatAbsolute(),
    spread = RuneSpread.PAST_PRESENT_FUTURE,
    topic = topic,
    reader = reader,
    recipient = recipient ?: "",
    question = question,
    qrPayload = toReadingQrPayload(),
    runes = listOf(
      pastRune.toReadingPdfDrawnRuneUiModel(Res.string.reading_tab_past_rune, Res.string.reading_position_past_description, topic),
      presentRune.toReadingPdfDrawnRuneUiModel(Res.string.reading_tab_present_rune, Res.string.reading_position_present_description, topic),
      futureRune.toReadingPdfDrawnRuneUiModel(Res.string.reading_tab_future_rune, Res.string.reading_position_future_description, topic),
    ),
  )

private fun DrawnRune.toReadingPdfDrawnRuneUiModel(
  position: StringResource? = null,
  positionDescription: StringResource? = null,
  topic: ReadingTopic,
): ReadingPdfDrawnRuneUiModel =
  ReadingPdfDrawnRuneUiModel(
    rune = rune,
    orientation = orientation,
    position = position,
    positionDescription = positionDescription,
    interpretation = generalInterpretation(),
    supplementalInterpretation = supplementalInterpretation(topic),
    keywords = generalKeywords(),
    supplementalKeywords = supplementalKeywords(topic),
  )
