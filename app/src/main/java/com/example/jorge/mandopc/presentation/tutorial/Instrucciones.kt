package com.example.jorge.mandopc.presentation.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme

class Instrucciones : ComponentActivity() {

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, Instrucciones::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MandoPcMovilTheme { // Wrap with your theme
                InstruccionesScreen()
            }
        }
    }
}