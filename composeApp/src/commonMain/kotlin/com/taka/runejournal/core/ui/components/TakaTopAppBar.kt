package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taka.runejournal.core.platform.AppBuildConfig
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.about_title
import taka_rune_journal.composeapp.generated.resources.app_name
import taka_rune_journal.composeapp.generated.resources.button_back
import taka_rune_journal.composeapp.generated.resources.button_close
import taka_rune_journal.composeapp.generated.resources.button_delete
import taka_rune_journal.composeapp.generated.resources.button_edit
import taka_rune_journal.composeapp.generated.resources.button_more_menu
import taka_rune_journal.composeapp.generated.resources.button_save
import taka_rune_journal.composeapp.generated.resources.design_system_title
import taka_rune_journal.composeapp.generated.resources.ic_app_icon
import taka_rune_journal.composeapp.generated.resources.ic_new_journal_entry_icon
import taka_rune_journal.composeapp.generated.resources.ic_new_reading_icon
import taka_rune_journal.composeapp.generated.resources.settings_title
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_journal_entry
import taka_rune_journal.composeapp.generated.resources.timeline_button_new_reading

enum class TakaTopBarNavigationIcon {
  None,
  Back,
  Close,
}

sealed class TakaTopBarAction {
  data object None : TakaTopBarAction()

  data class Save(
    val enabled: Boolean = true,
    val onClick: () -> Unit = {},
  ) : TakaTopBarAction()

  data class TimelineActions(
    val onNewReadingClick: () -> Unit,
    val onNewJournalEntryClick: () -> Unit,
    val onSettingsClick: () -> Unit,
    val onAboutClick: () -> Unit,
    val onDesignPlaygroundClick: () -> Unit
  ) : TakaTopBarAction()

  data class TimelineDetailActions(
    val onEditClick: () -> Unit,
    val onDeleteClick: () -> Unit
  ) : TakaTopBarAction()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakaTopBar(
  title: String? = null,
  navigationIcon: TakaTopBarNavigationIcon = TakaTopBarNavigationIcon.None,
  onNavigationClick: () -> Unit = {},
  action: TakaTopBarAction = TakaTopBarAction.None,
) {
  var isMenuExpanded by remember { mutableStateOf(false) }

  TopAppBar(
    title = { title?.let { Text(title) } },
    navigationIcon = {
      when (navigationIcon) {
        TakaTopBarNavigationIcon.None -> {
          IconButton(onClick = {}) {
            Image(
              painter = painterResource(Res.drawable.ic_app_icon),
              contentDescription = stringResource(Res.string.app_name),
              modifier = Modifier.size(32.dp),
            )
          }
        }

        TakaTopBarNavigationIcon.Back -> {
          IconButton(onClick = onNavigationClick) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = stringResource(Res.string.button_back),
            )
          }
        }

        TakaTopBarNavigationIcon.Close -> {
          IconButton(onClick = onNavigationClick) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = stringResource(Res.string.button_close),
            )
          }
        }
      }
    },
    actions = {
      when (action) {
        is TakaTopBarAction.None -> {}
        is TakaTopBarAction.Save -> {
          IconButton(
            onClick = action.onClick,
            enabled = action.enabled
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = stringResource(Res.string.button_save),
            )
          }
        }

        is TakaTopBarAction.TimelineActions -> {
          IconButton(
            onClick = action.onNewReadingClick
          ) {
            Icon(
              painter = painterResource(Res.drawable.ic_new_reading_icon),
              contentDescription = stringResource(Res.string.timeline_button_new_reading),
            )
          }
          IconButton(
            onClick = action.onNewJournalEntryClick
          ) {
            Icon(
              painter = painterResource(Res.drawable.ic_new_journal_entry_icon),
              contentDescription = stringResource(Res.string.timeline_button_new_journal_entry),
            )
          }
          IconButton(
            onClick = { isMenuExpanded = true }
          ) {
            Icon(
              imageVector = Icons.Default.MoreVert,
              contentDescription = stringResource(Res.string.button_more_menu),
              modifier = Modifier.offset(y = (-2).dp) // known issue that this icon is a little lower and needs manual adjustment
            )
          }

          DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
          ) {
            DropdownMenuItem(
              text = { Text(stringResource(Res.string.settings_title)) },
              onClick = {
                isMenuExpanded = false
                action.onSettingsClick()
              },
            )

            DropdownMenuItem(
              text = { Text(stringResource(Res.string.about_title)) },
              onClick = {
                isMenuExpanded = false
                action.onAboutClick()
              },
            )

            if (AppBuildConfig.isDebug) {
              DropdownMenuItem(
                text = { Text(stringResource(Res.string.design_system_title)) },
                onClick = {
                  isMenuExpanded = false
                  action.onDesignPlaygroundClick()
                },
              )
            }
          }
        }
        is TakaTopBarAction.TimelineDetailActions -> {
          IconButton(onClick = action.onEditClick) {
            Icon(
              imageVector = Icons.Outlined.Edit,
              contentDescription = stringResource(Res.string.button_edit),
            )
          }
          IconButton(onClick = action.onDeleteClick) {
            Icon(
              imageVector = Icons.Outlined.Delete,
              contentDescription = stringResource(Res.string.button_delete),
            )
          }
        }
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = Color.Transparent,
      scrolledContainerColor = Color.Transparent,
    ),
  )
}