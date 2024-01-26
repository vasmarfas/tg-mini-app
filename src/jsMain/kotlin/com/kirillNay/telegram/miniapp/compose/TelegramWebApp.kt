package com.kirillNay.telegram.miniapp.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import com.kirillNay.telegram.miniapp.compose.theme.ColorsConverter
import com.kirillNay.telegram.miniapp.compose.theme.TelegramThemeHandler
import com.kirillNay.telegram.miniapp.webApp.EventType
import com.kirillNay.telegram.miniapp.webApp.webApp
import org.jetbrains.skiko.wasm.onWasmReady

/**
 * Use [telegramWebApp] to provide Compose content of mini app.
 *
 * @param colorsConverter handles logic of converting telegram colors and color scheme (light or dark).
 * Pass custom colorsConverter to provide your own colors based on your design system.
 * It's recommended to create your own colors based on themeParams to provide smooth UX.
 *
 * @param telegramThemeHandler handles telegram theme switching (e.g light and dark mode switching).
 * Pass custom themeHandler in case you have your own logic of theme switching.
 *
 * @param animationDuration defines duration of animation of switching colors.
 *
 * @param content is compose content of your mini app.
 *
 */
@OptIn(ExperimentalComposeUiApi::class)
fun telegramWebApp(
    colorsConverter: ColorsConverter = ColorsConverter.Default(),
    telegramThemeHandler: TelegramThemeHandler = TelegramThemeHandler.Default(colorsConverter),
    typography: Typography? = null,
    shapes: Shapes? = null,
    animationDuration: Int = 0,
    content: @Composable (ViewPortValues) -> Unit
) {
    onWasmReady {
        CanvasBasedWindow {
            var paddings by remember { mutableStateOf(ViewPortValues(webApp.viewportHeight.dp, webApp.viewportStableHeight.dp)) }

            LaunchedEffect(true) {
                webApp.addEventHandler(EventType.VIEWPORT_CHANGED) {
                    paddings = ViewPortValues(webApp.viewportHeight.dp, webApp.viewportStableHeight.dp)
                }
            }

            MaterialTheme(
                    colors = if (animationDuration == 0) telegramThemeHandler.colors else telegramThemeHandler.colors.switch(animationDuration),
                    typography = typography ?: MaterialTheme.typography,
                    shapes = shapes ?: MaterialTheme.shapes,
                    content = { content(paddings) }
            )
        }
    }
}

@Composable
fun Colors.switch(
        duration: Int
) = copy(
        primary = animateColor(primary, duration),
        primaryVariant = animateColor(primaryVariant, duration),
        secondary = animateColor(secondary, duration),
        secondaryVariant = animateColor(secondaryVariant, duration),
        background = animateColor(background, duration),
        surface = animateColor(surface, duration),
        error = animateColor(error, duration),
        onPrimary = animateColor(onPrimary, duration),
        onSecondary = animateColor(onSecondary, duration),
        onBackground = animateColor(onBackground, duration),
        onSurface = animateColor(onSurface, duration),
        onError = animateColor(onError, duration)
)

@Composable
private fun animateColor(
        targetValue: Color,
        duration: Int
) = animateColorAsState(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = duration)
).value