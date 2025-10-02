package com.example.jorge.mandopc.presentation.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jorge.mandopc.presentation.controller.model.ControllerAction
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme
import com.example.jorge.mandopc.utilities.DragHandler
import org.koin.android.ext.android.inject

class ControllerActivity : ComponentActivity() {

    private val presenter: ControllerPresenter by inject()
    private val dragHandler: DragHandler by inject()

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

        presenter.invokeAction(ControllerAction.CreateConnection(ip))

        setContent {
            val state by presenter.state.collectAsStateWithLifecycle()

            MandoPcMovilTheme {
                ControllerScreen(
                    state = state,
                    pointerInputEventHandler = {
                        dragHandler.handleGestures(
                            scope = this,
                            onDrag = { dragX, dragY ->
                                presenter.invokeAction(ControllerAction.MoveMouse(dragX, dragY))
                            },
                            onDragFinish = {
                                presenter.invokeAction(ControllerAction.DragFinish)
                            },
                        )
                    },
                    onLeftClick = {
                        presenter.invokeAction(ControllerAction.LeftClick)
                    },
                    onRightClick = {
                        presenter.invokeAction(ControllerAction.RightClick)
                    },
                    onReconnect = { },
                    onKeyboard = { }
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.invokeAction(ControllerAction.Disconnect)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.invokeAction(ControllerAction.Disconnect)
    }
}
