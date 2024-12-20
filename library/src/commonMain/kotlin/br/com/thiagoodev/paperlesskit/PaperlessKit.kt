package br.com.thiagoodev.paperlesskit

/**
 * Multiplatform implementation of the PaperlessKit interface for handling PDF operations.
 *
 * This class provides platform-specific implementations for scanning, picking,
 * and opening PDFs. The actual implementation is expected to be provided for
 * each supported platform (e.g., Android, iOS).
 */
expect class PaperlessKit : PaperlessKitInterface {
    /**
     * Scans a document to create a PDF using the provided options.
     *
     * @param options The configuration options for the scanning operation, such as camera source and save path.
     * @return A [PDF] object representing the scanned document.
     */
    override suspend fun scan(options: Options): PDF

    /**
     * Allows the user to pick an existing PDF from the device storage.
     *
     * @return A [PDF] object representing the selected document.
     */
    override suspend fun pick(): PDF

    /**
     * Opens the specified PDF for viewing.
     *
     * @param pdf The [PDF] object to be opened.
     */
    override suspend fun open(pdf: PDF)

    /**
     * Opens a PDF file from the specified file path.
     *
     * @param path The file path of the PDF to be opened.
     */
    override suspend fun open(path: String)
}
