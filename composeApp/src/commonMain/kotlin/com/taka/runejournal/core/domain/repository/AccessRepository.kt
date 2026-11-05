package com.taka.runejournal.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AccessRepository {

  val hasFullAccess: Flow<Boolean>

  suspend fun setHasFullAccess(hasFullAccess: Boolean)
}
