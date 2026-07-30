package com.taka.runejournal.feature.reading.ui

sealed class DrawState {
  sealed class Choose : DrawState() {
    data object Idle : Choose()
    data object Shaking : Choose()
    data object Dragging : Choose()
  }

  sealed class Reveal : DrawState() {
    data object CenteringRunes : Reveal()
    data object UnveilingGlyphs : Reveal()
    data object CompletingAnimations : Reveal()
  }
}