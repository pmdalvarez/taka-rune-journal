import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_cancel
import taka_rune_journal.composeapp.generated.resources.button_delete
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_confirm_text
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_confirm_text_no_name
import taka_rune_journal.composeapp.generated.resources.timeline_delete_dialog_title

@Composable
fun DeleteTimelineEntryDialog(
  onDismiss: () -> Unit,
  onConfirm: () -> Unit,
  name: String?,
  type: String
) {
  val confirmText = if (name.isNullOrBlank()) {
    stringResource(Res.string.timeline_delete_dialog_confirm_text_no_name)
  } else {
    stringResource(Res.string.timeline_delete_dialog_confirm_text, name)
  }
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(stringResource(Res.string.timeline_delete_dialog_title, type)) },
    text = { Text(confirmText) },
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