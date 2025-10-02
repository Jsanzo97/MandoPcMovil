package com.example.jorge.mandopc.presentation.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jorge.mandopc.presentation.theme.AppPrimary
import com.example.jorge.mandopc.presentation.theme.AppSecondary
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme

internal typealias PointerInputEventHandler = suspend PointerInputScope.() -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControllerScreen(
    pointerInputEventHandler: PointerInputEventHandler,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppPrimary),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppSecondary)
                    .pointerInput(Unit) { pointerInputEventHandler() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Touchpad",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ControllerScreenPreview() {
    MandoPcMovilTheme {
        ControllerScreen(
            pointerInputEventHandler = { awaitEachGesture {} }
        )
    }
}
