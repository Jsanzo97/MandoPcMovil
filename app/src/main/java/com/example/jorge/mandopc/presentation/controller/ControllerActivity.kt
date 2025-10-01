package com.example.jorge.mandopc.presentation.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme
import com.example.jorge.mandopc.data.remote.SocketSender
import kotlinx.coroutines.launch

class ControllerActivity : ComponentActivity() {

    companion object {

        const val IP_ADDRESS = "IP_ADDRESS"
        fun getIntent(context: Context, ip: String): Intent {
            val intent = Intent(context, ControllerActivity::class.java)
            intent.putExtra(IP_ADDRESS, ip)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ip = intent.getStringExtra(IP_ADDRESS) ?: ""

        val client = SocketSender(ip = ip)
        lifecycleScope.launch {
            client.connect()
        }

        setContent {
            MandoPcMovilTheme {
                MandoScreen(
                    onClick = {
                        lifecycleScope.launch {
                            client.sendImmediate("ClickarI")
                        }
                    },
                    onDrag = { dragX, dragY ->
                        Log.d("Controller:", "DRAG $dragX,$dragY")
                        lifecycleScope.launch {
                            client.sendThrottled("Mov $dragX,$dragY")
                        }
                    },
                    onDragFinish = {
                        lifecycleScope.launch {
                            client.sendImmediate("Mov restart")
                        }
                    }
                )
            }
        }
    }
}