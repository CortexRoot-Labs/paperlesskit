package br.com.cortexrootlabs.paperlesskit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
actual fun PaperlessKit(content: @Composable (ScanAction, State<Pair<PDF?, Exception?>>) -> Unit) {

}