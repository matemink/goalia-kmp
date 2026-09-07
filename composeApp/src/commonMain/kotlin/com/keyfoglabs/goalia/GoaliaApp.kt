package com.keyfoglabs.goalia

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.keyfoglabs.goalia.ui.MatchesScreen

private val GoaliaColors = lightColorScheme(
    primary = Color(0xFF006B4F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF9BF2D0),
    background = Color(0xFFF7FAF7),
    surface = Color.White,
    surfaceVariant = Color(0xFFE2E9E4)
)

@Composable
fun GoaliaApp() {
    MaterialTheme(colorScheme = GoaliaColors) {
        MatchesScreen()
    }
}
