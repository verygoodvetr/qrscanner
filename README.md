# QR Scanner Pro (Android)

Premium-quality QR and barcode scanner built with **Kotlin + Jetpack Compose**, following **MVVM**, **Hilt DI**, **Room**, **Navigation**, and **CameraX + ML Kit**.

## Highlights
- Real-time QR/barcode scanning (QR, EAN, UPC, Code128-ready via ML Kit).
- Smart action handling for URL, Wi-Fi payloads, contact cards, email, phone, location, and text.
- Scan history with favorites persisted locally (Room, offline-first).
- QR generator (text/URL/Wi-Fi/contact payload compatible).
- Material 3 design with dark/light mode and one-handed floating actions.
- Settings for haptic/sound toggles and offline mode indicator.
- Navigation graph includes Splash, Scanner, Result, History, Generator, and Settings.

## Architecture
```
UI (Compose Screens + ViewModels)
    ↓
Domain (UseCases + Models)
    ↓
Data (Repository)
    ↓
Local Storage (Room + DataStore)
```

## Tech Stack
- Kotlin
- Jetpack Compose (Material 3)
- MVVM + StateFlow
- Jetpack Navigation
- CameraX
- Google ML Kit Barcode Scanning
- Room Database
- Hilt Dependency Injection
- DataStore Preferences
- ZXing (QR generation)

## Build & Run
1. Open project in **Android Studio Iguana+**.
2. Ensure Android SDK 35 is installed.
3. Sync Gradle.
4. Run app on physical device (camera required) or emulator with virtual camera support.

### CLI build
```bash
./gradlew :app:assembleDebug
```

## Notes
- URL safety warnings are shown for suspicious or malformed hosts before launching.
- Scanning is designed to work offline once app dependencies are installed.
- Extend `ResultScreen` with richer native handlers (Wi-Fi connect / contact save) as needed by OEM policy and permissions.

