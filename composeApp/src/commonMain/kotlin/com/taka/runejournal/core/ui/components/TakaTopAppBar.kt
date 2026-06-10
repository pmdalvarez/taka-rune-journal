package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
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
import org.jetbrains.compose.resources.painterResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_app_icon

enum class TakaTopBarNavigationIcon {
  None,
  Back,
  Close,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakaTopBar(
  title: String? = null,
  navigationIcon: TakaTopBarNavigationIcon = TakaTopBarNavigationIcon.None,
  onNavigationClick: () -> Unit = {},
  showMoreMenu: Boolean = false,
  onSettingsClick: () -> Unit = {},
  onAboutClick: () -> Unit = {},
) {
  var isMenuExpanded by remember { mutableStateOf(false) }

  TopAppBar(
    title = { title?.let { Text(title) } },
    navigationIcon = {
      when (navigationIcon) {
        TakaTopBarNavigationIcon.None -> {
          Image(
            painter = painterResource(Res.drawable.ic_app_icon),
            contentDescription = "Taka",
            modifier = Modifier
              .padding(start = 16.dp)
              .size(32.dp),
          )
        }

        TakaTopBarNavigationIcon.Back -> {
          IconButton(onClick = onNavigationClick) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
            )
          }
        }

        TakaTopBarNavigationIcon.Close -> {
          IconButton(onClick = onNavigationClick) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
            )
          }
        }
      }
    },
    actions = {
      if (showMoreMenu) {
        Box {
          IconButton(
            onClick = { isMenuExpanded = true }
          ) {
            Icon(
              imageVector = Icons.Default.MoreVert,
              contentDescription = "More options",
            )
          }

          DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
          ) {
            DropdownMenuItem(
              text = { Text("Settings") },
              onClick = {
                isMenuExpanded = false
                onSettingsClick()
              },
            )

            DropdownMenuItem(
              text = { Text("About") },
              onClick = {
                isMenuExpanded = false
                onAboutClick()
              },
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