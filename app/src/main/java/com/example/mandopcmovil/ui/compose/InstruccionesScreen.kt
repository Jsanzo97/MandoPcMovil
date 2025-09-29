package com.example.mandopcmovil.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InstruccionesScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Content for InstruccionesScreen can be added here
    }
}

@Preview(showBackground = true, name = "Instrucciones Screen Preview")
@Composable
fun InstruccionesScreenPreview() {
    InstruccionesScreen()
}
