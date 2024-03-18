# Telegram mini app
Library for creating telegram [mini apps](https://core.telegram.org/bots/webapps) with Kotlin Multiplatform and Compose Multiplatform.

## Setup
1. Create Kotlin Multiplatform app with js target. Check [this tutorial](https://www.jetbrains.com/lp/compose-multiplatform/) for more information.

2. Place script `telegram-web-app.js` in the `<head>` tag:
```
<script src="https://telegram.org/js/telegram-web-app.js"></script>
```
3. Add implementation for `jsMain` `tg-mini-app` library:
```
implementation("io.github.kirillNay:tg-mini-app:1.1.0")
```
4. In `main` funtion of `jsMain` call `telegramWebApp` with providing Composable content:
```
fun main() {
   telegramWebApp { style ->
      // Compose content ...
   }
}
```
## WebApp

To get access of Telegram web app instance call `webApp`. All properties and functions of [Telegram WebApp](https://core.telegram.org/bots/webapps#initializing-mini-apps) are available in kotlin style.

#### Example
Showing confirmation dialog before closing app.
```
webApp.showConfirm("Want to exit?") { result ->
  if (result) {
      webApp.close()
  }
}
```

## Designing

Mini apps should be designed to provide smooth user experience so pay attention of themeing of app. Check [Telegram's Design Guidelines](https://core.telegram.org/bots/webapps#design-guidelines) for more information.

### Viewport
Pay attention to viewport changes. Viewport of mini app can change due to users gestures. On every change new viewport size value will be provided to `content` composable as a `TelegramStyle` parameter. Recompose composable functions that should be changed on viewport size modifications.

You can get current viewport height using `webApp.viewportHeight`. In case you need last stable viewport state you can get it from `webApp.viewportStableHeight`.
Aldo you can get if current mini app state is expanded using `webApp.isExpanded`.

#### Example
```
telegramWebApp { style ->
      val height = remember(style.viewPort.viewPortHeight) {
          max(style.viewPort.viewPortHeight, 500.dp)
      }
      Column(
          modifier = Modifier.height(height)
      ) {
         /...
      }
}
```

<img src="https://github.com/kirillNay/tg-mini-app/assets/56832972/c8a96b4b-1839-4a1e-adf9-4fb2ddc1c7ee" width="296" height="584"/>


### Colors
The colors of the mini-apps should not contrast with the main theme of the application. Create your system design based on `colors` parameter that is provided to `content` composable as a `TelegramStyle`. This parameter will change and cause recomposition if user change app style in settings of telegram app or in using dark theme of his device.

You can observe theme changes in any place of your app using:
```
var telegramColors by remember { mutableStateOf(YourDefaultColors()) }
webApp.addEventHandler(EventType.THEME_CHANGED) {
   telegramColors = webApp.themeParams.toYourAppColors() // Map telegram theme colors to your colors scheme
}
```

You can get if current theme of app is in dark mode using `webApp.colorScheme`.

<img src="https://github.com/kirillNay/tg-mini-app/assets/56832972/ebe022f5-8316-44d6-9f6f-c1f9f1a37feb" width="296" height="584"/>


## Sample

Check [GlassOfWater](https://github.com/kirillNay/GlassOfWater-client) - simple web app created with Kotlin and Compose Multiplatform.
