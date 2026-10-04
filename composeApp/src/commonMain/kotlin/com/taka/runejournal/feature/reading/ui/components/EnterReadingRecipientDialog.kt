package com.taka.runejournal.feature.reading.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.taka.runejournal.core.ui.components.TakaTextField
import com.taka.runejournal.core.ui.theme.TakaContentSpacing
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.button_cancel
import taka_rune_journal.composeapp.generated.resources.button_continue
import taka_rune_journal.composeapp.generated.resources.reading_recipient_dialog_body
import taka_rune_journal.composeapp.generated.resources.reading_recipient_dialog_name
import taka_rune_journal.composeapp.generated.resources.reading_recipient_dialog_title

@Composable
fun EnterReadingRecipientDialog(
  recipient: String?,
  onDismiss: () -> Unit,
  onConfirm: (String) -> Unit,
) {
  var recipientInput by rememberSaveable(recipient) {
    mutableStateOf(recipient.orEmpty())
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = MaterialTheme.shapes.small,
    containerColor = MaterialTheme.colorScheme.surface,
    title = {
      Text(
        text = stringResource(Res.string.reading_recipient_dialog_title),
      )
    },
    text = {
      Column {
        Text(
          text = stringResource(Res.string.reading_recipient_dialog_body),
          style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(TakaContentSpacing))

        TakaTextField(
          value = recipientInput,
          onValueChange = { recipientInput = it },
          label = stringResource(Res.string.reading_recipient_dialog_name),
          singleLine = true,
        )
      }
    },
    confirmButton = {
      TextButton(
        onClick = { onConfirm(recipientInput.trim()) },
        enabled = recipientInput.isNotBlank(),
      ) {
        Text(stringResource(Res.string.button_continue))
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(stringResource(Res.string.button_cancel))
      }
    },
  )
}