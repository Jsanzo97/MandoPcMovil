package com.example.jorge.mandopc.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.example.jorge.mandopc.presentation.home.model.HomeState
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
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
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(32.dp)
                    .clickable {
                        keyboardController?.hide()
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = ipAddress,
                        onValueChange = { ipAddress = it },
                        label = { Text("Introduzca aqui la IP") },
                        singleLine = true,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = setDefaultIp,
                            onCheckedChange = { setDefaultIp = it }
                        )
                        Text(
                            text = "Establecer como predeterminada",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            keyboardController?.hide()
                            onConnect(ipAddress, setDefaultIp)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Conectar")
                    }


                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            keyboardController?.hide()
                            onTutorials()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
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
    MandoPcMovilTheme {
        HomeScreen(
            state = HomeState.Ready(),
            onConnect = { _, _ -> },
            onTutorials = { }
        )
    }
}
