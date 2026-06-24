import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_cancel
import taka_rune_journal.composeapp.generated.resources.button_delete
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_body
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_title

@Composable
fun DeleteTimelineEntryDialog(
  onDismiss: () -> Unit,
  onConfirm: () -> Unit,
  title: String,
  preview: String?
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(stringResource(Res.string.timeline_delete_dialog_title, title)) },
    text = {
      Column {
        Text(
          text = stringResource(Res.string.timeline_delete_dialog_body),
          style = MaterialTheme.typography.bodyMedium
        )

        if (!preview.isNullOrBlank()) {
          Spacer(modifier = Modifier.height(16.dp))

          Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = preview,           // Your preview text
              style = MaterialTheme.typography.bodyMedium,
              maxLines = 4,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.padding(16.dp)
            )
          }
        }
      }
     },
    confirmButton = {
      TextButton(onClick = onConfirm) {
        Text(stringResource(Res.string.button_delete))
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(stringResource(Res.string.button_cancel))
      }
    }
  )
}