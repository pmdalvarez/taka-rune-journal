import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.taka.runejournal.core.ui.components.TakaCard
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_cancel
import taka_rune_journal.composeapp.generated.resources.button_delete
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_body

@Composable
fun DeleteTimelineEntryDialog(
  onDismiss: () -> Unit,
  onConfirm: () -> Unit,
  title: String,
  preview: String?
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape =  MaterialTheme.shapes.small,
    containerColor = MaterialTheme.colorScheme.surface,
    title = {
      Text(title)
    },
    text = {
      Column {
        Text(
          text = stringResource(Res.string.timeline_delete_dialog_body),
          style = MaterialTheme.typography.bodyMedium
        )

        if (!preview.isNullOrBlank()) {
          Spacer(modifier = Modifier.height(TakaContentSpacing))

          TakaCard {
            Text(
              text = preview,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              maxLines = 4,
              overflow = TextOverflow.Ellipsis
            )
          }
        }
      }
    },
    confirmButton = {
      TextButton(onClick = onConfirm) {
        Text(
          text = stringResource(Res.string.button_delete),
          color = MaterialTheme.colorScheme.error,
        )
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(stringResource(Res.string.button_cancel))
      }
    }
  )
}