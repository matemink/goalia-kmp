# Goalia

[![CI](https://github.com/matemink/goalia-kmp/actions/workflows/ci.yml/badge.svg)](https://github.com/matemink/goalia-kmp/actions/workflows/ci.yml)

Kotlin Multiplatform client for football match predictions. The shared Compose UI runs on Android and iOS and consumes the separate [Goalia backend](https://github.com/matemink/goalia-backend).

## Features

- Displays scheduled and completed matches in a scrollable list.
- Shows team and competition logos from the API.
- Highlights the predicted home win, draw, or away win.
- Displays prediction probabilities and completed-match accuracy.
- Supports pull-to-refresh, loading, empty, and error states.

## Stack

Kotlin Multiplatform, Compose Multiplatform, Ktor, Kotlin Serialization, Coroutines, and Coil.

## Run

Open the project in Android Studio and run `composeApp` for Android. On macOS, open `iosApp/iosApp.xcodeproj` in Xcode for iOS.

The API endpoint is configured in `FootballDataApi.kt`. The backend must be running and accessible to the selected device or simulator.
