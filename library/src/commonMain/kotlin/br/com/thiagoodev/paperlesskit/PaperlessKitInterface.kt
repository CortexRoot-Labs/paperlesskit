package br.com.thiagoodev.paperlesskit

/**
 * Defines the contract for handling PDF scanning, picking, and opening operations.
 */
interface PaperlessKitInterface {
    /**
     * Scans a document to create a PDF using the provided options.
     *
     * @param options The configuration options for the scanning operation, such as camera source and save path.
     * @return A [PDF] object representing the scanned document.
     */
    suspend fun scan(options: Options): PDF

    /**
     * Allows the user to pick an existing PDF from the device storage.
     *
     * @return A [PDF] object representing the selected document.
     */
    suspend fun pick(): PDF

    /**
     * Opens the specified PDF for viewing.
     *
     * @param pdf The [PDF] object to be opened.
     */
    suspend fun open(pdf: PDF)

    /**
     * Opens a PDF file from the specified file path.
     *
     * @param path The file path of the PDF to be opened.
     */
    suspend fun open(path: String)
}
