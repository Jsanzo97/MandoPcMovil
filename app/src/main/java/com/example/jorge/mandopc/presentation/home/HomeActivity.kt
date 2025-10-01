package com.example.jorge.mandopc.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jorge.mandopc.presentation.controller.ControllerActivity
import com.example.jorge.mandopc.presentation.home.model.HomeAction
import com.example.jorge.mandopc.presentation.home.model.HomeState
import com.example.jorge.mandopc.presentation.theme.MandoPcMovilTheme
import com.example.jorge.mandopc.presentation.tutorial.Instrucciones
import org.koin.android.ext.android.inject

class HomeActivity : ComponentActivity() {

    private val presenter: HomePresenter by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.invokeAction(HomeAction.RetrieveDefaultIp)

        setContent {
            val state = presenter.state.collectAsStateWithLifecycle().value

            when (state) {
                is HomeState.Ready -> {
                    MandoPcMovilTheme {
                        MainScreen(
                            state = presenter.state.collectAsStateWithLifecycle().value,
                            onConnect = { ip, isDefault ->
                                presenter.invokeAction(HomeAction.Connect(ip, isDefault))
                            },
                            onTutorials = {
                                startActivity(Instrucciones.getIntent(this))
                            }
                        )
                    }
                }

                is HomeState.Success -> {
                    startActivity(
                        ControllerActivity.getIntent(
                            context = this,
                            ip = state.ip
                        )
                    )
                    finish()
                }
            }
        }
    }
}