package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF0F1214),
    surface = Color(0xFF161B1E),
    onPrimary = Color(0xFF001D36),
    onBackground = Color(0xFFE2E2E6),
    onSurface = Color(0xFFE2E2E6),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = EditorialPrimary,
    secondary = EditorialTextSecondary,
    tertiary = EditorialHighlightText,
    background = EditorialBg,
    surface = EditorialWhite,
    onPrimary = EditorialWhite,
    onSecondary = EditorialWhite,
    onTertiary = EditorialWhite,
    onBackground = EditorialTextMain,
    onSurface = EditorialTextMain,
    surfaceVariant = EditorialHighlightBg,
    onSurfaceVariant = EditorialHighlightText,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Set default dynamicColor to false to preserve the customized Editorial Aesthetic design theme
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
