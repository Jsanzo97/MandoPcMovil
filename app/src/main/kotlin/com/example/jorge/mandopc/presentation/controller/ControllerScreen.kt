package com.example.jorge.mandopc.presentation.controller

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jorge.mandopc.presentation.controller.model.ControllerState
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme

internal typealias PointerInputEventHandler = suspend PointerInputScope.() -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ControllerScreen(
    state: ControllerState,
    pointerInputEventHandler: PointerInputEventHandler,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onReconnect: () -> Unit,
    onKeyboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val touchpadWeight = if (isLandscape) 8f else 9f
    val buttonRowWeight = if (isLandscape) 2f else 1f

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(
                text = "Connected: ${state is ControllerState.Connected}",
                color = MaterialTheme.colorScheme.onBackground
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .weight(touchpadWeight)
                    .pointerInput(Unit) { pointerInputEventHandler() }
                    .clickable {
                        onLeftClick()
                    },
                contentAlignment = Alignment.Center
            ) {}

            HorizontalDivider(
                thickness = 2.dp,
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
                    thickness = 2.dp,
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(buttonRowWeight),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = onReconnect,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Reconnect")
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = onKeyboard,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Keyboard")
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=360dp,height=640dp"
)
@Composable
fun ControllerScreenPreview() {
    MandoPcMovilTheme {
        ControllerScreen(
            state = ControllerState.Ready,
            pointerInputEventHandler = { awaitEachGesture {} },
            onLeftClick = {},
            onRightClick = {},
            onReconnect = {},
            onKeyboard = {}
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
            state = ControllerState.Ready,
            pointerInputEventHandler = { awaitEachGesture {} },
            onLeftClick = {},
            onRightClick = {},
            onReconnect = {},
            onKeyboard = {}
        )
    }
}
