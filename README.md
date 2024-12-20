<div style="display: flex; align-items: center;">
    <h1 style="margin-right: 10px;">PaperlessKit (PLK) - Multiplatform Library for Document Scanning</h1>
    <img src="./images/icon.png" alt="PaperlessKit Logo" style="height: 200px;">
</div>


**PaperlessKit** (PLK) is a multiplatform library designed to simplify document scanning, editing, and PDF handling. With support for **Kotlin Multiplatform**, this library enables you to create mobile apps for **Android** and **iOS** with powerful features for intelligent document scanning.

### Features

- **Intelligent Scanning:** Capture documents using the device's camera with the ability to edit points and perspective of the image.
- **PDF Conversion:** Convert scanned images into PDF files.
- **Open PDFs:** Open PDFs from both local device storage and newly scanned PDFs.
- **Multiplatform:** Supports Android and iOS, allowing you to use the same codebase across both platforms.

### Installation

To use the PaperlessKit (PLK) library in your project, add the dependency in your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("br.com.thiagoodev.paperlesskit:paperlesskit:<version>")
}
```

### How to Use

Here are some examples of how to implement **PaperlessKit** (PLK) in a **Kotlin Multiplatform** and **Compose Multiplatform** application.

#### Example 1: Scanning a Document

```kotlin
import br.com.thiagoodev.paperlesskit.*

suspend fun scanDocument() {
    val options = Options(
        savePath = "/path/to/save/pdf",
        camera = CameraSource.BACK
    )

    val pdf: PDF = PaperlessKit().scan(options)
    // Now you have the scanned PDF and can save or open the file
}
```

#### Example 2: Picking a PDF from Storage

```kotlin
import br.com.thiagoodev.paperlesskit.*

suspend fun pickPdfFromStorage() {
    val pdf: PDF = PaperlessKit().pick()
    // The selected PDF can now be opened or processed
}
```

#### Example 3: Opening a Newly Scanned PDF

```kotlin
import br.com.thiagoodev.paperlesskit.*

suspend fun openScannedPdf(pdf: PDF) {
    PaperlessKit().open(pdf)
}
```

#### Example 4: Opening a PDF from Storage

```kotlin
import br.com.thiagoodev.paperlesskit.*

suspend fun openPdfFromPath(path: String) {
    PaperlessKit().open(path)
}
```

### How It Works

The `PaperlessKit` class implements the `PaperlessKitInterface` and provides the following functions:

- **scan(options: Options): PDF**: Scans a document, applying the camera settings and save path provided in `Options`, and returns a `PDF` object representing the scanned document.
- **pick(): PDF**: Allows the user to pick a PDF from the local device storage.
- **open(pdf: PDF)**: Opens a newly scanned or loaded PDF.
- **open(path: String)**: Opens a PDF from a specified file path.

### Customization

- **Camera:** The library allows you to choose between the front or rear camera for scanning documents, using the `camera` parameter in `Options` (default: **BACK**).
- **Save Path:** You can specify where the PDF will be saved, or leave it as `null` to save it to a default location.

### About the Creator

**PaperlessKit** was developed by **Thiago Sousa** ([@thiagoodev](https://github.com/thiagoodev)) as part of an effort to provide a simple and effective solution for document scanning. This library aims to make it easy to integrate document scanning and PDF handling features into multiplatform apps.

---

### Contributions

If you would like to contribute to **PaperlessKit**, feel free to open issues or pull requests in the official repository. All contributions are welcome!

### License

This library is licensed under the **Apache License 2.0**.
