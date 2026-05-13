package com.taka.runejournal.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taka.runejournal.core.model.RuneId
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.rune_empty

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Reusable starter for Kotlin Multiplatform Compose apps.",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun RunePreview() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RuneId.entries
            .chunked(4)
            .forEach { rowRunes ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowRunes.forEach { runeId ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(runeId.drawable),
                                contentDescription = runeId.key,
                                modifier = Modifier.size(width = 48.dp, height = 72.dp)
                            )

                            Text(
                                text = runeId.key,
                                modifier = Modifier.padding(top = 4.dp),
                                color = Color.Black,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.rune_empty),
                    contentDescription = "empty",
                    modifier = Modifier.size(width = 48.dp, height = 72.dp)
                )

                Text(
                    text = "empty",
                    modifier = Modifier.padding(top = 4.dp),
                    color = Color.Black,
                    fontSize = 10.sp
                )
            }
        }
    }
}