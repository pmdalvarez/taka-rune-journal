import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@Composable
fun getScreenWidth(): Dp {
  return with(LocalDensity.current) {
    LocalWindowInfo.current.containerSize.width.toDp()
  }
}