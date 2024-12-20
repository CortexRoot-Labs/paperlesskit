package br.com.thiagoodev.paperlesskit

/**
 * Configuration options for capturing and saving a PDF using the camera.
 *
 * @property savePath The optional file path where the captured PDF should be saved.
 *                    If null, a default path will be used or the PDF may not be saved automatically.
 * @property camera The camera source to be used for capturing the PDF.
 *                  Defaults to the rear-facing camera (CameraSource.BACK).
 */
data class Options(
    /**
     * The optional file path where the captured PDF should be saved.
     * If null, a default path may be used or the PDF may not be saved automatically.
     */
    val savePath: String?,

    /**
     * The camera source to be used for capturing the PDF.
     * Defaults to the rear-facing camera (CameraSource.BACK).
     */
    val camera: CameraSource = CameraSource.BACK,
)
