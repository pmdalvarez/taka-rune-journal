package com.taka.runejournal.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.taka.runejournal.core.data.local.TakaDatabase
import com.taka.runejournal.core.platform.AppBuildConfig
import org.koin.dsl.module

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
  name = "settings"
)

fun androidModule(context: Context) = module {

  single<DataStore<Preferences>> {
    context.applicationContext.settingsDataStore
  }

  single<TakaDatabase> {
    Room.databaseBuilder(
      context.applicationContext,
      TakaDatabase::class.java,
      "taka_database.db",
    )
    .build()
  }

  single {
    get<TakaDatabase>().timelineItemDao()
  }
}