package com.example.jorge.mandopc

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.jorge.mandopc.bd.IpDbHelper
import com.example.mandopcmovil.ui.compose.MainScreen
import com.example.mandopcmovil.ui.theme.MandoPcMovilTheme

class MainActivity : ComponentActivity() {

    private val INTERVALO = 2000
    private var tiempoPrimerClick: Long = 0

    private lateinit var ipDbHelper: IpDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ipDbHelper = IpDbHelper(this)
        val initialIpAddress = ipDbHelper.readIp("1") ?: ""

        setContent {
            MandoPcMovilTheme { // Wrap with your theme
                val keyboardController = LocalSoftwareKeyboardController.current

                MainScreen(
                    initialIp = initialIpAddress,
                    onConectarClick = { ip, esPredeterminada ->
                        keyboardController?.hide()
                        if (ip.isBlank()) {
                            Toast.makeText(this@MainActivity, "Introduzca la ip", Toast.LENGTH_SHORT).show()
                        } else {
                            if (esPredeterminada) {
                                val currentSavedIp = ipDbHelper.readIp("1")
                                if (ip != currentSavedIp) {
                                    if (currentSavedIp != null) {
                                        ipDbHelper.deleteIp("1")
                                    }
                                }
                                ipDbHelper.insertIp(ip)
                            }
                            val con = Intent(this@MainActivity, Mando::class.java)
                            con.putExtra("ip", ip)
                            startActivity(con)
                        }
                    },
                    onInstruccionesClick = {
                        keyboardController?.hide()
                        val ins = Intent(this@MainActivity, Instrucciones::class.java)
                        startActivity(ins)
                    }
                )
            }
        }
    }

    override fun onBackPressed() {
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show()
        }
        tiempoPrimerClick = System.currentTimeMillis()
    }
}
