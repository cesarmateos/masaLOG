package com.example.masalog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = GrisOscuro,
    onPrimary= Color.White,
    background = Color.Black,
    onBackground = Color.White,
    primaryVariant = Purple700,
    secondary = GrisClaro
)

private val LightColorPalette = lightColors(
    primary = VioletaOscuro,
    primaryVariant = VioletaClaro,
    secondary = Naranja,
    secondaryVariant = GrisClaro,
    onPrimary = Color.White,
    onBackground = GrisOscuro,
    onSecondary = GrisOscuro,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MasaLOGTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}