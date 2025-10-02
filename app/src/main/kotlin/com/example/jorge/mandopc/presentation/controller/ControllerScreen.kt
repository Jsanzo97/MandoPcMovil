package com.example.jorge.mandopc.presentation.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jorge.mandopc.presentation.theme.AppPrimary
import com.example.jorge.mandopc.presentation.theme.AppSecondary
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandoScreen(
    onClick: () -> Unit,
    onDrag: ((dragX: Int, dragY: Int) -> Unit),
    onDragFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppPrimary),
    ) {
        var pointerDownPosition by remember { mutableStateOf<Offset?>(null) }
        var pointerUpPosition by remember { mutableStateOf<Offset?>(null) }
        var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
        var isDragging by remember { mutableStateOf(false) }

        val dragThreshold = 10f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppSecondary)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()

                                event.changes.forEach { change ->
                                    when {
                                        change.pressed && change.previousPressed.not() -> {
                                            pointerDownPosition = change.position
                                            currentDragPosition = change.position
                                            isDragging = false
                                            change.consume()
                                        }

                                        // Pointer Drag (moving while pressed)
                                        change.pressed && change.position != Offset.Zero -> {
                                            currentDragPosition = change.position
                                            onDrag(
                                                change.position.x.toInt() - pointerDownPosition!!.x.toInt(),
                                                change.position.y.toInt() - pointerDownPosition!!.y.toInt()
                                            )
                                            pointerDownPosition?.let { downPos ->
                                                val distance = (currentDragPosition!! - downPos).getDistance()
                                                if (distance > dragThreshold) {
                                                    isDragging = true
                                                }
                                            }
                                            change.consume()
                                        }

                                        !change.pressed && change.previousPressed -> {
                                            pointerUpPosition = change.position
                                            pointerDownPosition?.let { downPos ->
                                                val distance = (pointerUpPosition!! - downPos).getDistance()

                                                if (!(isDragging && distance > dragThreshold)) {
                                                    onClick()
                                                } else {
                                                    onDragFinish()
                                                }
                                            }

                                            isDragging = false
                                            change.consume()
                                        }
                                    }
                                }
                            }
                        }
                    },
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
fun MandoScreenPreview() {
    MandoPcMovilTheme {
        MandoScreen(
            onClick = {},
            onDrag = { _, _ -> },
            onDragFinish = {}
        )
    }
}
