package com.example.jorge.mandopc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mandopcmovil.ui.compose.InstruccionesScreen
import com.example.mandopcmovil.ui.theme.MandoPcMovilTheme // Import the theme

class Instrucciones : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MandoPcMovilTheme { // Wrap with your theme
                InstruccionesScreen()
            }
        }
    }
}
