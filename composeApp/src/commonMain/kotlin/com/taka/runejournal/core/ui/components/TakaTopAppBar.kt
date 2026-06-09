package com.taka.runejournal.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakaTopBar(
  title: String? = null,
  canNavigateBack: Boolean,
  onBackClick: () -> Unit,
  showMoreMenu: Boolean = false,
  onSettingsClick: () -> Unit = {},
  onAboutClick: () -> Unit = {},
) {
  var isMenuExpanded by remember { mutableStateOf(false) }

  TopAppBar(
    title = { title?.let { Text(title) } },
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(onClick = onBackClick) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
          )
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