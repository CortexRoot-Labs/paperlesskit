package br.com.thiagoodev.paperlesskit

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

actual class PaperlessKit : AppCompatActivity(), PaperlessKitInterface {
    val options = GmsDocumentScannerOptions
        .Builder()
        .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_FULL)
        .setPageLimit(1)
        .setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF)
        .setGalleryImportAllowed(true)
        .build()
    val scanner: GmsDocumentScanner = GmsDocumentScanning.getClient(options)
    val scannerLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(StartIntentSenderForResult(), ::onActivityResult)

    private fun onActivityResult(result: ActivityResult) {
        if(result.resultCode == RESULT_OK) {
            val docResult = GmsDocumentScanningResult.fromActivityResultIntent(result.data)
            docResult?.pdf?.let { pdf ->
                val pdfUri = pdf.uri
                val pageCount = pdf.pageCount
            }
        }
    }

    actual override suspend fun scan(options: Options): PDF {
        scanner.getStartScanIntent(this)
            .addOnSuccessListener { intentSender ->
                scannerLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
            }
            .addOnFailureListener {

            }
    }

    actual override suspend fun pick(): PDF {
        TODO("Not yet implemented")
    }

    actual override suspend fun open(pdf: PDF) {
        TODO("Not yet implemented")
    }

    actual override suspend fun open(path: String) {
        TODO("Not yet implemented")
    }

    fun log(message: String) {
        Log.println(Log.INFO, "PaperlessKit", message)
    }
}