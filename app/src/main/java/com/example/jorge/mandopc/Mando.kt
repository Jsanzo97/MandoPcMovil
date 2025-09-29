package com.example.jorge.mandopc

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.jorge.mandopc.utilities.enviar
import com.example.jorge.mandopc.utilities.ip
import com.example.mandopcmovil.ui.compose.MandoScreen
import com.example.mandopcmovil.ui.theme.MandoPcMovilTheme

class Mando : ComponentActivity() {
    private var inicioClick: Long = 0L

    private var origenX: Float = 0f
    private var origenY: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentIp = intent.getStringExtra("ip")
        if (intentIp != null) {
            ip = intentIp
        }

        setContent {
            MandoPcMovilTheme {
                var tecladoComposeValue by remember { mutableStateOf("") }
                val tecladoFocusRequester = remember { FocusRequester() }
                val keyboardController = LocalSoftwareKeyboardController.current
                val configuration = LocalConfiguration.current
                val density = LocalDensity.current

                val touchpadWidthPx = remember { with(density) { 350.dp.toPx() } }
                val rightClickThresholdPx = remember { touchpadWidthPx / 2 }

                LaunchedEffect(configuration.orientation) {
                    val rotation = windowManager.defaultDisplay.rotation
                    if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                        tecladoFocusRequester.requestFocus()
                        keyboardController?.show()
                    } else {
                        keyboardController?.hide()
                    }
                }

                MandoScreen(
                    tecladoValue = tecladoComposeValue,
                    onTecladoValueChange = { newValue ->
                        if (newValue.isNotEmpty()) {
                            enviar("Tec $newValue", ip)
                        }
                        tecladoComposeValue = ""
                    },
                    onTecladoImeAction = {
                        enviar("Enter", ip)
                        keyboardController?.hide()
                    },
                    tecladoFocusRequester = tecladoFocusRequester,
                    onTouchpadDown = { position ->
                        origenX = position.x
                        origenY = position.y
                        inicioClick = System.currentTimeMillis()
                    },
                    onTouchpadMove = { dragAmount ->
                        val despX = dragAmount.x
                        val despY = dragAmount.y
                        enviar("Mov $despX,$despY", ip)
                    },
                    onTouchpadUp = { clickDuration, finalPointerPosition ->
                        enviar("Mov restart", ip)
                        if (clickDuration < 100) {
                            if (finalPointerPosition.x > rightClickThresholdPx) {
                                enviar("ClickarD", ip)
                            } else {
                                enviar("ClickarI", ip)
                            }
                        }
                    },
                    onSubirVolClick = { enviar("SubirVol", ip) },
                    onBajarVolClick = { enviar("BajarVol", ip) },
                    onSubirBrilloClick = { enviar("SubirBrillo", ip) },
                    onBajarBrilloClick = { enviar("BajarBrillo", ip) }
                )
            }
        }
    }
}
