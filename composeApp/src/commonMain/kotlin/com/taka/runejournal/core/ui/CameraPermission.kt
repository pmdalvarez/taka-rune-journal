package com.taka.runejournal.core.ui

import androidx.compose.runtime.Composable

/**
 * Returns whether camera permission is granted, and requests it on first composition if not.
 */
@Composable
expect fun rememberCameraPermission(): Boolean
