package com.taka.runejournal.feature.reading.ui

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

actual class RuneHapticFeedback(
  context: Context,
) {
  private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    val vibratorManager =
      context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

    vibratorManager.defaultVibrator
  } else {
    @Suppress("DEPRECATION")
    context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
  }

  actual fun playStoneClink(strength: Float) {
    if (!vibrator.hasVibrator()) return

    val amplitude = calculateAmplitude(strength)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      vibrator.vibrate(
        VibrationEffect.createWaveform(
          longArrayOf(
            0L,
            FIRST_CLINK_DURATION_MILLIS,
            CLINK_PAUSE_MILLIS,
            SECOND_CLINK_DURATION_MILLIS,
          ),
          intArrayOf(
            0,
            amplitude,
            0,
            (amplitude * SECOND_CLINK_AMPLITUDE_MULTIPLIER).toInt(),
          ),
          NO_REPEAT,
        ),
      )
    } else {
      @Suppress("DEPRECATION")
      vibrator.vibrate(FALLBACK_CLINK_DURATION_MILLIS)
    }
  }

  private fun calculateAmplitude(strength: Float): Int {
    val clampedStrength = strength.coerceIn(
      minimumValue = MIN_SHAKE_STRENGTH,
      maximumValue = STRONG_SHAKE_STRENGTH,
    )

    val strengthProgress =
      (clampedStrength - MIN_SHAKE_STRENGTH) /
          (STRONG_SHAKE_STRENGTH - MIN_SHAKE_STRENGTH)

    return (MIN_CLINK_AMPLITUDE + strengthProgress * CLINK_AMPLITUDE_RANGE)
      .toInt()
      .coerceIn(MIN_CLINK_AMPLITUDE, MAX_CLINK_AMPLITUDE)
  }

  companion object {
    private const val MIN_SHAKE_STRENGTH = 4f
    private const val STRONG_SHAKE_STRENGTH = 12f

    private const val MIN_CLINK_AMPLITUDE = 40
    private const val MAX_CLINK_AMPLITUDE = 120
    private const val CLINK_AMPLITUDE_RANGE =
      MAX_CLINK_AMPLITUDE - MIN_CLINK_AMPLITUDE

    private const val FIRST_CLINK_DURATION_MILLIS = 8L
    private const val CLINK_PAUSE_MILLIS = 18L
    private const val SECOND_CLINK_DURATION_MILLIS = 5L
    private const val SECOND_CLINK_AMPLITUDE_MULTIPLIER = 0.45f

    private const val FALLBACK_CLINK_DURATION_MILLIS = 12L
    private const val NO_REPEAT = -1
  }
}

@Composable
actual fun rememberRuneHapticFeedback(): RuneHapticFeedback {
  val context = LocalContext.current

  return remember(context) {
    RuneHapticFeedback(context)
  }
}