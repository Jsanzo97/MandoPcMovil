package com.example.jorge.mandopc.presentation.controller

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme

internal typealias PointerInputEventHandler = suspend PointerInputScope.() -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ControllerScreen(
    pointerInputEventHandler: PointerInputEventHandler,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onKeyPressed: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(" ", TextRange(1))) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val touchpadWeight = if (isLandscape) 8f else 9f
    val buttonRowWeight = if (isLandscape) 2f else 1f

    var shouldShowKeyboard by remember { mutableStateOf(false) }

    LaunchedEffect(shouldShowKeyboard) {
        if (shouldShowKeyboard) {
            focusRequester.requestFocus()
            keyboardController?.show()
            shouldShowKeyboard = false
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            keyboardController?.hide()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .weight(touchpadWeight)
                        .pointerInput(Unit) { pointerInputEventHandler() }
                        .clickable {
                            keyboardController?.hide()
                            onLeftClick()
                        },
                    contentAlignment = Alignment.Center
                ) {}

                HorizontalDivider(
                    thickness = 4.dp,
                    color = MaterialTheme.colorScheme.background
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(buttonRowWeight)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .weight(1f)
                            .clickable {
                                onLeftClick()
                            }
                    ) {}

                    VerticalDivider(
                        thickness = 4.dp,
                        color = MaterialTheme.colorScheme.background
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .weight(1f)
                            .clickable {
                                onRightClick()
                            }
                    ) {}
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            shouldShowKeyboard = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Keyboard")
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(1.dp)
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newTextFieldValue ->
                    val oldText = textFieldValue.text
                    val newText = newTextFieldValue.text

                    when {
                        newText.length < oldText.length -> {
                            onKeyPressed("Delete")
                        }
                        newText.contains("\n") -> {
                            onKeyPressed("Enter")
                        }
                        newText.length > oldText.length -> {
                            val addedChar = newText.last()
                            if (addedChar == ' ') {
                                onKeyPressed("Tec $SPACE")
                            } else {
                                onKeyPressed("Tec $addedChar")
                            }
                        }
                    }
                    textFieldValue = TextFieldValue(" ", TextRange(1))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0f)
                    .focusRequester(focusRequester)
            )
        }
    }
}

private const val SPACE = " "

@Preview(
    showBackground = true,
    device = "spec:width=360dp,height=640dp"
)
@Composable
fun ControllerScreenPreview() {
    MandoPcMovilTheme {
        ControllerScreen(
            pointerInputEventHandler = { awaitEachGesture {} },
            onLeftClick = {},
            onRightClick = {},
            onKeyPressed = {}
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=640dp,height=360dp"
)
@Composable
fun ControllerLandscapeScreenPreview() {
    MandoPcMovilTheme {
        ControllerScreen(
            pointerInputEventHandler = { awaitEachGesture {} },
            onLeftClick = {},
            onRightClick = {},
            onKeyPressed = {}
        )
    }
}
