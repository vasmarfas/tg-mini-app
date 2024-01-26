package com.kirillNay.telegram.miniapp.compose.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kirillNay.telegram.miniapp.webApp.EventType
import com.kirillNay.telegram.miniapp.webApp.webApp

/**
 * Handles theme changes of mini app. Default implementation configure colors based on telegram app theme changes.
 */
interface TelegramThemeHandler {

    val colors: Colors

    @Stable
    class Default(colorsConverter: ColorsConverter): TelegramThemeHandler {

        override var colors by mutableStateOf(colorsConverter.convert(TelegramColors.fromWebApp(), webApp.colorScheme))

        init {
            webApp.addEventHandler(EventType.THEME_CHANGED) {
                colors = colorsConverter.convert(TelegramColors.fromWebApp(), webApp.colorScheme)
            }
        }

    }

}
