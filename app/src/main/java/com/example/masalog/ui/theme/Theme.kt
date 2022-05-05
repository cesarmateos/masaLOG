package com.example.masalog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = GrisOscuro,
    onPrimary= Color.White,
    primaryVariant = MoradoMuyOscuro,
    secondary = MoradoSuave,
    secondaryVariant = VioletaOscuro,
    onSecondary = MoradoMuySuave,
    background = Color.Black,
    onBackground = Color.White,
    surface = NaranjaOscuro
)

private val LightColorPalette = lightColors(
    primary = VioletaOscuro,
    onPrimary = Color.White,
    primaryVariant = VioletaClaro,
    secondary = Naranja,
    onSecondary = GrisOscuro,
    secondaryVariant = GrisClaro,
    onBackground = GrisOscuro,
    surface = NaranjaMuySuave
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