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

## Exact Run Instructions

### Option A — Android Studio (recommended)
1. Open the folder in **Android Studio**.
2. In **Settings > Build Tools > Gradle**, set **Gradle JDK = 17**.
3. Let Gradle sync finish.
4. Connect an Android device (camera required) or start an emulator.
5. Press **Run**.

### Option B — Command line (no wrapper required)
```bash
# 1) Ensure Java 17 is active
java -version

# 2) Build debug APK
gradle :app:assembleDebug

# 3) (optional) install on connected device
gradle :app:installDebug
```

## If Gradle Build Fails

### 1) Binary-policy / push blocks
Some Git hosting policies reject commits containing binaries.
This repo avoids committing `gradle/wrapper/gradle-wrapper.jar` for that reason.

If you still want wrapper commands locally, generate wrapper files only on your machine:
```bash
gradle wrapper --gradle-version 8.14.3 --no-validate-url
```
(Do not commit the generated JAR if your policy blocks binaries.)

### 2) Java version problems
Use **JDK 17** for Android Gradle Plugin 8.4.x:
```bash
export JAVA_HOME="/path/to/jdk17"
export PATH="$JAVA_HOME/bin:$PATH"
java -version
```

### 3) Network / proxy issues downloading dependencies
If Gradle cannot download dependencies, configure proxy in `~/.gradle/gradle.properties`.

## Notes
- URL safety warnings are shown for suspicious or malformed hosts before launching.
- Scanning is designed to work offline once app dependencies are installed.
- Extend `ResultScreen` with richer native handlers (Wi-Fi connect / contact save) as needed by OEM policy and permissions.
