// androidMain/core/ui/ShakeDetectorEffect.android.kt
package com.taka.runejournal.core.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import kotlin.math.sqrt

@Composable
actual fun ShakeDetectorEffect(
  onShakeImpulse: (direction: Offset, strength: Float) -> Unit,
) {
  val context = LocalContext.current
  val currentOnShakeImpulse = rememberUpdatedState(onShakeImpulse)

  DisposableEffect(Unit) {
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

    var lastShakeTimeMillis = 0L

    val shakeThreshold = 4.0f
    val shakeCooldownMillis = 120L

    val listener = object : SensorEventListener {
      override fun onSensorChanged(event: SensorEvent) {
        val accelerationX = event.values[0]
        val accelerationY = event.values[1]
        val shakeStrength =  sqrt(accelerationX * accelerationX + accelerationY * accelerationY)

        if (shakeStrength <= shakeThreshold) return  // we only react to shake that exceeds this shakeThreshold (m/s²)

        val now = System.currentTimeMillis()
        if (now - lastShakeTimeMillis < shakeCooldownMillis) return
        lastShakeTimeMillis = now

        val direction =
          Offset(
            x = accelerationX / shakeStrength,
            y = accelerationY / shakeStrength,
          )

        currentOnShakeImpulse.value(
          direction,
          shakeStrength,
        )
      }

      override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int, ) {
        // No implementation needed for shake detection
      }
    }

    if (accelerometer != null) {
      sensorManager.registerListener(
        listener,
        accelerometer,
        SensorManager.SENSOR_DELAY_GAME,
      )
    }

    onDispose {
      sensorManager.unregisterListener(listener)
    }
  }
}