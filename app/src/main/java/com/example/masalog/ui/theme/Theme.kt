package com.example.masalog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val VioletaOscuro = Color(0xFF76608A)
val VioletaClaro = Color(0xffe9d1ff)
val Naranja = Color(0xFFFFAB0F)
val GrisOscuro = Color(0xFF514D46)
val GrisClaro = Color(0xFFDBDBDB)
val NaranjaSuave = Color(0xFFFCD2A2)
val NaranjaOscuro = Color(0xFFcc7a28)
val NaranjaMuySuave = Color(0xFFFCEFEA)
val NaranjaPython = Color(0xffFF9933)
val MoradoSuave = Color(0xffC5ADF2)
val MoradoMuySuave = Color(0xffFCF2FF)
val MoradoMuyOscuro = Color(0xff432D57)

val VerdeOK = Color(0XFF52c33e)

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    onBackground = GrisOscuro
)

private val LightColorPalette = lightColors(
    primary = VioletaOscuro,
    primaryVariant = VioletaClaro,
    secondary = Naranja,
    onBackground = GrisOscuro,
    onSecondary = GrisOscuro

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