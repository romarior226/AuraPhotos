
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.unplashapi.ui.theme.Typography

// Light warm palette
val Amber900 = Color(0xFF7C3B00)
val Amber700 = Color(0xFFB05A00)
val Amber500 = Color(0xFFE07B39)
val Amber300 = Color(0xFFF4A96A)
val Amber100 = Color(0xFFFDE8D5)
val Amber50  = Color(0xFFFFF6EE)

val Brown800 = Color(0xFF3E2010)
val Brown600 = Color(0xFF6B3A1F)
val Brown400 = Color(0xFF9C6241)

val Cream    = Color(0xFFFFFBF5)
val CreamDark= Color(0xFFF5EDE0)

val Sand800  = Color(0xFF2C1A0E)
val Sand700  = Color(0xFF3D2510)
val Sand600  = Color(0xFF5C3820)
val Sand200  = Color(0xFFD4B49A)
val Sand100  = Color(0xFFEDD9C6)

val ErrorWarm = Color(0xFFB53A2F)
val ErrorLight= Color(0xFFFFDAD4)

private val LightColors = lightColorScheme(
    primary          = Amber700,
    onPrimary        = Color.White,
    primaryContainer = Amber100,
    onPrimaryContainer = Amber900,

    secondary        = Brown600,
    onSecondary      = Color.White,
    secondaryContainer = Sand100,
    onSecondaryContainer = Brown800,

    tertiary         = Amber500,
    onTertiary       = Color.White,
    tertiaryContainer = Amber100,
    onTertiaryContainer = Amber900,

    background       = Cream,
    onBackground     = Sand800,

    surface          = Cream,
    onSurface        = Sand800,
    surfaceVariant   = CreamDark,
    onSurfaceVariant = Sand600,

    outline          = Sand200,
    outlineVariant   = Sand100,

    error            = ErrorWarm,
    onError          = Color.White,
    errorContainer   = ErrorLight,
    onErrorContainer = Color(0xFF410002),
)

private val DarkColors = darkColorScheme(
    primary          = Amber300,
    onPrimary        = Brown800,
    primaryContainer = Amber700,
    onPrimaryContainer = Amber100,

    secondary        = Sand200,
    onSecondary      = Brown800,
    secondaryContainer = Brown600,
    onSecondaryContainer = Sand100,

    tertiary         = Amber300,
    onTertiary       = Brown800,
    tertiaryContainer = Amber700,
    onTertiaryContainer = Amber100,

    background       = Sand800,
    onBackground     = Sand100,

    surface          = Sand800,
    onSurface        = Sand100,
    surfaceVariant   = Sand700,
    onSurfaceVariant = Sand200,

    outline          = Brown400,
    outlineVariant   = Sand600,

    error            = Color(0xFFFFB4AB),
    onError          = Color(0xFF690005),
    errorContainer   = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
)

@Composable
fun UnplashApiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}