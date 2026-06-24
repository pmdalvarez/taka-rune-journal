import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
    shape =  MaterialTheme.shapes.small,
    containerColor = MaterialTheme.colorScheme.surface,
    title = {
      Text(stringResource(Res.string.timeline_delete_dialog_title, title))
    },
    text = {
      Column {
        Text(
          text = stringResource(Res.string.timeline_delete_dialog_body),
          style = MaterialTheme.typography.bodyMedium
        )

        if (!preview.isNullOrBlank()) {
          Spacer(modifier = Modifier.height(16.dp))

          Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
            border = BorderStroke(
              width = 1.dp,
              color = MaterialTheme.colorScheme.outlineVariant,
            ),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = preview,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
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