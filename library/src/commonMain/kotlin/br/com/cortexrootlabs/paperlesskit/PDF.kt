package br.com.cortexrootlabs.paperlesskit

/**
 * Represents a PDF document with its metadata and content.
 *
 * @property name The name of the PDF file, including its extension.
 * @property bytes The content of the PDF as a byte array.
 *                 Useful for processing or transmitting the document.
 * @property path The file path where the PDF is stored on the device.
 *                This can be used to locate the file in the filesystem.
 */
data class PDF(
    /**
     * The name of the PDF file, including its extension.
     */
    val name: String,

    /**
     * The content of the PDF as a byte array.
     * Useful for processing or transmitting the document.
     */
    val bytes: ByteArray,

    /**
     * The file path where the PDF is stored on the device.
     * This can be used to locate the file in the filesystem.
     */
    val path: String,
)
