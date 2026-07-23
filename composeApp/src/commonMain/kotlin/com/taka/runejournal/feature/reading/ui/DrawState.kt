package com.taka.runejournal.feature.reading.ui

sealed class DrawState {
  sealed class Choose : DrawState() {
    data object Idle : Choose()
    data object Shaking : Choose()
    data object Dragging : Choose()
  }

  data object Reveal : DrawState()
}