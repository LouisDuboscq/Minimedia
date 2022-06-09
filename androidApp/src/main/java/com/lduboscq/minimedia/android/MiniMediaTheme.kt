package com.lduboscq.minimedia.android

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val lightColorScheme = lightColorScheme(
    surface = Color(0XFF141b4d),
    surfaceVariant = Color.White, // cards background
    onSurface = Color.Black, // texts, icons, etc
)

@Composable
fun MiniMediaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme,
        content = content
    )
}
