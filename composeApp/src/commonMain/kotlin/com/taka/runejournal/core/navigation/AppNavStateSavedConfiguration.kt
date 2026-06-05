package com.taka.runejournal.core.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.taka.runejournal.feature.reading.navigation.ReadingRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val appNavSavedStateConfiguration = SavedStateConfiguration {
  serializersModule = SerializersModule {
    polymorphic(NavKey::class) {
      subclass(AppRoute::class, AppRoute.serializer())
      subclass(ReadingRoute::class, ReadingRoute.serializer())    }
  }
}