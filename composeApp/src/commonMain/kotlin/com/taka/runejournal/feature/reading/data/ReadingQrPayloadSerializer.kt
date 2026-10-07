package com.taka.runejournal.feature.reading.data.model


import com.taka.runejournal.feature.reading.domain.model.ReadingQrPayload
import kotlinx.serialization.json.Json

private val readingQrJson = Json {
  classDiscriminator = "version"
  ignoreUnknownKeys = true
}

fun ReadingQrPayload.encode(): String =
  readingQrJson.encodeToString(
    ReadingQrPayload.serializer(),
    this,
  )

fun String.decodeReadingQrPayload(): ReadingQrPayload =
  readingQrJson.decodeFromString(
    ReadingQrPayload.serializer(),
    this,
  )
