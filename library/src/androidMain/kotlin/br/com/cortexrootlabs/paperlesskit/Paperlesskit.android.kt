package br.com.cortexrootlabs.paperlesskit

import android.app.Activity
import android.content.IntentSender
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toFile
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import java.io.File
import java.lang.Exception

const val LOG_TAG: String = "PaperlessKit"

@Composable
actual fun PaperlessKit(content: @Composable (ScanAction, State<Pair<PDF?, Exception?>>) -> Unit) {

    val options = GmsDocumentScannerOptions.Builder()
        .setGalleryImportAllowed(false)
        .setPageLimit(1)
        .setResultFormats(RESULT_FORMAT_PDF)
        .setScannerMode(SCANNER_MODE_FULL)
        .build()

    val activity = LocalContext.current as? ComponentActivity
        ?: throw IllegalStateException("Context is not a ComponentActivity")

    val state = remember { mutableStateOf<Pair<PDF?, Exception?>>(Pair(null, null)) }
    val scanner = remember { GmsDocumentScanning.getClient(options) }

    val scannerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { handleScannerResult(it, state) }

    content({ scan(activity, scanner, scannerLauncher, state) }, state)
}

private fun handleScannerResult(result: ActivityResult, state: MutableState<Pair<PDF?, Exception?>>) {
    if (result.resultCode == Activity.RESULT_OK) {
        val scanningResult = GmsDocumentScanningResult.fromActivityResultIntent(result.data)
        scanningResult?.pdf?.let { pdf -> state.value = Pair(pdf.toPDF(), null) }
    } else {
        Log.d(LOG_TAG, "Scanning was not successful. Result code: ${result.resultCode}")
    }
}

typealias ScannerLauncher = ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>

private fun scan(
    activity: ComponentActivity,
    scanner: GmsDocumentScanner,
    scannerLauncher: ScannerLauncher,
    state: MutableState<Pair<PDF?, Exception?>>,
) {
    scanner.getStartScanIntent(activity)
        .addOnSuccessListener { addOnSuccessListener(scannerLauncher, it) }
        .addOnFailureListener { addOnFailureListener(it, state) }
        .addOnCanceledListener(::addOnCanceledListener)
}

private fun addOnSuccessListener(scannerLauncher: ScannerLauncher, intentSender: IntentSender) {
    scannerLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
}

private fun addOnFailureListener(exception: Exception, state: MutableState<Pair<PDF?, Exception?>>) {
    Log.d(LOG_TAG, exception.message ?: "Error!")
    state.value = Pair(null, exception)
}

private fun addOnCanceledListener() {
    Log.d(LOG_TAG, "Scanning was not successful")
}

private fun GmsDocumentScanningResult.Pdf.toPDF(): PDF {
    val file: File = uri.toFile()

    return PDF(
        name = file.name,
        path = uri.path ?: "",
        bytes = file.readBytes()
    )
}
