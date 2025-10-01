package com.example.jorge.mandopc.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jorge.mandopc.presentation.common.CustomToolbar
import com.example.jorge.mandopc.presentation.home.model.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    state: HomeState,
    onConnect: (ip: String, isDefault: Boolean) -> Unit,
    onTutorials: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is HomeState.Ready -> {
            val keyboardController = LocalSoftwareKeyboardController.current

            var ipAddress by remember { mutableStateOf(state.defaultIp) }
            var setDefaultIp by remember { mutableStateOf(false) }

            Column(
                modifier = modifier.fillMaxSize()
            ) {
                CustomToolbar()

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    OutlinedTextField(
                        value = ipAddress,
                        onValueChange = { ipAddress = it },
                        label = { Text("Introduzca aqui la IP") },
                        singleLine = true,
                        modifier = Modifier.width(215.dp)
                    )

                    Spacer(Modifier.height(13.dp))

                    Row(
                        modifier = Modifier.widthIn(max = 214.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = setDefaultIp,
                            onCheckedChange = { setDefaultIp = it }
                        )
                        Text(
                            text = "Establecer como predeterminada",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Spacer(Modifier.height(28.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            onConnect(ipAddress, setDefaultIp)
                        },
                        modifier = Modifier.width(217.dp)
                    ) {
                        Text("Conectar")
                    }

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            onTutorials()
                        },
                        modifier = Modifier.width(215.dp)
                    ) {
                        Text("Instrucciones")
                    }
                }
            }
        }
        is HomeState.Success -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        state = HomeState.Ready(),
        onConnect = { _, _ -> },
        onTutorials = { }
    )
}
