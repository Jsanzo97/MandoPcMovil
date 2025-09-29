package com.example.mandopcmovil.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.ui.input.pointer.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mandopcmovil.ui.theme.MandoPcMovilTheme

@Composable
private fun MandoToolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mando Pc",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 50.dp)
        )
        Text(
            text = "Develop by Jsanzo97®",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(start = 100.dp, top = 5.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandoScreen(
    tecladoValue: String,
    onTecladoValueChange: (String) -> Unit,
    onTecladoImeAction: () -> Unit,
    tecladoFocusRequester: FocusRequester,
    onTouchpadDown: (position: Offset) -> Unit,
    onTouchpadMove: (dragAmount: Offset) -> Unit,
    onTouchpadUp: (clickDuration: Long, finalPointerPosition: Offset) -> Unit,
    onSubirVolClick: () -> Unit,
    onBajarVolClick: () -> Unit,
    onSubirBrilloClick: () -> Unit,
    onBajarBrilloClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var startTime by remember { mutableLongStateOf(0L) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MandoToolbar()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(350.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.secondary)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val down: PointerInputChange = awaitFirstDown(requireUnconsumed = true, pass = PointerEventPass.Main)
                                startTime = System.currentTimeMillis()
                                onTouchpadDown(down.position)
                                down.consume()

                                var pointerId = down.id

                                while (true) {
                                    val event: PointerEvent = awaitPointerEvent(PointerEventPass.Main)
                                    val changes = event.changes
                                    var stillPressed = false
                                    var pointerReleased = false
                                    var finalPosition = Offset.Zero

                                    for (change in changes) {
                                        if (change.id == pointerId) {
                                            if (change.pressed) {
                                                stillPressed = true
                                                val positionChange = change.positionChange()
                                                if (positionChange != Offset.Zero) {
                                                    onTouchpadMove(positionChange)
                                                }
                                                change.consume()
                                            } else {
                                                val clickDuration = System.currentTimeMillis() - startTime
                                                finalPosition = change.position
                                                onTouchpadUp(clickDuration, finalPosition)
                                                change.consume()
                                                pointerReleased = true
                                            }
                                        } else {
                                            change.consume()
                                        }
                                    }

                                    if (pointerReleased || !stillPressed) {
                                        break
                                    }
                                }
                            }
                        }
                    }
                    .padding(top = 150.dp),
                contentAlignment = Alignment.TopCenter
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 9.dp)
        ) {
            val buttonDefaultColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.secondary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onSubirBrilloClick,
                    modifier = Modifier.weight(1f),
                    colors = buttonDefaultColors
                ) { Text("BRILLO +") }
                Spacer(Modifier.width(48.dp))
                Button(
                    onClick = onBajarBrilloClick,
                    modifier = Modifier.weight(1f),
                    colors = buttonDefaultColors
                ) { Text("BRILLO -") }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onSubirVolClick,
                    modifier = Modifier.weight(1f),
                    colors = buttonDefaultColors
                ) { Text("VOL +") }
                Spacer(Modifier.width(48.dp))
                Button(
                    onClick = onBajarVolClick,
                    modifier = Modifier.weight(1f),
                    colors = buttonDefaultColors
                ) { Text("VOL -") }
            }

            Spacer(Modifier.height(5.dp))

            OutlinedTextField(
                value = tecladoValue,
                onValueChange = onTecladoValueChange,
                modifier = Modifier
                    .width(215.dp)
                    .focusRequester(tecladoFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onTecladoImeAction() }
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    cursorColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
                placeholder = null,
                label = null
            )
        }
    }
}

@Preview(showBackground = true, name = "Mando Screen Preview")
@Composable
fun MandoScreenPreview() {
    MandoPcMovilTheme {
        val focusRequester = remember { FocusRequester() }
        var textValue by remember { mutableStateOf("") }
        MandoScreen(
            tecladoValue = textValue,
            onTecladoValueChange = { textValue = it },
            onTecladoImeAction = {},
            tecladoFocusRequester = focusRequester,
            onTouchpadDown = {},
            onTouchpadMove = {},
            onTouchpadUp = { _, _ -> },
            onSubirVolClick = {},
            onBajarVolClick = {},
            onSubirBrilloClick = {},
            onBajarBrilloClick = {}
        )
    }
}
