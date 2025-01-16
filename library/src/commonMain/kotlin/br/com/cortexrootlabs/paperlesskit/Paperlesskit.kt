package br.com.cortexrootlabs.paperlesskit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

typealias ScanAction = () -> Unit

@Composable
expect fun PaperlessKit(content: @Composable (ScanAction, State<Pair<PDF?, Exception?>>) -> Unit)
