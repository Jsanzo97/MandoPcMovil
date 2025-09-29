package com.example.mandopcmovil.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val colorPrimary = Color(0xFF3F51B5)
val colorWhite = Color.White

@Composable
private fun CustomToolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorPrimary)
            .heightIn(min = 56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Mando Pc",
            color = colorWhite,
            modifier = Modifier.padding(start = 50.dp)
        )
        Text(
            text = "Develop by Jsanzo97®",
            fontSize = 18.sp,
            color = colorWhite,
            modifier = Modifier.padding(end = 16.dp, top = 5.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    initialIp: String = "", 
    onConectarClick: (ip: String, esPredeterminada: Boolean) -> Unit,
    onInstruccionesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var ipAddress by remember(initialIp) { mutableStateOf(initialIp) } 
    var esPredeterminada by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CustomToolbar()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = (186 - 56).dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    checked = esPredeterminada,
                    onCheckedChange = { esPredeterminada = it }
                )
                Text(
                    text = "Establecer como predeterminada",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(Modifier.height(28.dp))

            Button(
                onClick = { onConectarClick(ipAddress, esPredeterminada) },
                modifier = Modifier.width(217.dp)
            ) {
                Text("Conectar")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onInstruccionesClick,
                modifier = Modifier.width(215.dp)
            ) {
                Text("Instrucciones")
            }
        }
    }
}

@Preview(showBackground = true, name = "Main Screen Preview")
@Composable
fun MainScreenPreview() {
    MainScreen(
        initialIp = "192.168.1.100", 
        onConectarClick = { _, _ -> },
        onInstruccionesClick = { }
    )
}
