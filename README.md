# BarcodeScanner

An Android application for scanning barcodes with optional GS1 parsing support. The project consists
of:

- app: Android app (Jetpack Compose UI + Hilt DI) that captures barcodes, displays results and
  history, and lets you configure GS1 parsing behavior.
- parser: A pure Kotlin library that parses GS1 barcodes based on a JSON definition of Application
  Identifiers (AIs).

Both modules are built with Gradle (Kotlin DSL). The repository includes unit tests for the parser
module.

## Features

- Camera-based barcode scanning.
- Supports multiple barcode formats (configurable in-app).
- GS1 barcode detection and parsing.
    - Adjustable FNC1 and group separator (GS) characters to match your scanner/data configuration.
- History of scanned results with details view.
- Easy copying of values from results.
- State persistence for user settings and scan history.

## Project Structure

- app/
    - Main Android application, UI screens, state & DI.
    - Notable packages:
        - scanner/domain: Camera image analysis and analyzer factory.
        - scanner/presentation: ViewModel and UI models.
        - scanner/presentation/screens: Compose screens (camera, overview, details).
        - scanner/peristance: Simple persistence of settings and results.
        - core: Hilt setup and app wiring.
- parser/
    - gs1parser: GS1Scanner and related data models.
    - utils: Result type and JSON resource reader.
    - resources/gs1.json: Application Identifier (AI) metadata used by the parser.
    - tests: Unit tests for the parser utilities and GS1 parsing.

## Requirements

- Android Studio (Giraffe/Koala/Latest stable)
- Android Gradle Plugin compatible with the included Gradle wrapper
- JDK 17+ (use the JDK bundled with Android Studio if possible)
- Android device or emulator running API level that matches your app/build.gradle.kts config (
  typically 24+)

## Build & Run

Option A: Android Studio

1. Open the project folder (G:/projects/BarcodeScanner) in Android Studio.
2. Let Gradle sync finish.
3. Select the app run configuration.
4. Connect a device or start an emulator.
5. Click Run.

Option B: Command line

- Windows (PowerShell or cmd):
    - gradlew.bat assembleDebug
    - APK output will be under app/build/outputs/apk/debug/

## Using the App

- Grant camera permission on first launch.
- From the camera screen, point the camera at a barcode. Detected results will appear in the scan
  history.
- Tap a result to see details; GS1 barcodes will be parsed into AI/value pairs when possible.
- Use the settings drawer (overview screen) to:
    - Enable/disable specific barcode formats.
    - Configure GS1 parsing characters
    - Reset settings to defaults.

Note: The exact defaults are defined in app code (BarcodeScannerState and constants such as
FNC1_DEFAULT and GS_DEFAULT).

## GS1 Parsing Details

- The app checks if scanned text is GS1 via parser.isGs1Format(...).
- If it is GS1, the parser attempts to parse into a list of values per the AIs in
  parser/src/main/resources/gs1.json.
- Parsed values and any parse errors are surfaced in the UI.
- The FNC1 and GS characters are configurable in-app to match your scanner’s encoding.

Relevant classes:

- app/src/main/java/com/app/barcodescanner/scanner/presentation/model/BarcodeScannerViewModel.kt
- parser/src/main/kotlin/gs1parser/GS1Scanner.kt

## Running Tests

- From Android Studio: run tests in the parser module (e.g., parser/src/test/kotlin).
- From command line:
    - Windows: .\parser\gradlew.bat test

Example tests included:

- parser/src/test/kotlin/GS1ScannerTest.kt
- parser/src/test/kotlin/JsonResourceReaderTest.kt
- parser/src/test/kotlin/result/ResultTest.kt