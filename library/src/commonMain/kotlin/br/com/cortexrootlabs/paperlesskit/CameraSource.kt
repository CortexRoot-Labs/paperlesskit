package br.com.cortexrootlabs.paperlesskit

/**
 * Represents the source of the camera to be used for capturing a PDF.
 *
 * @property FRONT The front-facing camera of the device, used for capturing PDFs.
 * @property BACK The rear-facing camera of the device, used for capturing PDFs.
 */
enum class CameraSource {
    /**
     * The front-facing camera of the device.
     * Can be used to capture PDFs when needed.
     */
    FRONT,

    /**
     * The rear-facing camera of the device.
     * Typically used to capture PDFs for better quality and alignment.
     */
    BACK;
}
