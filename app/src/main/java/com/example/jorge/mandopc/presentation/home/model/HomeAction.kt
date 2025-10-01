package com.example.jorge.mandopc.presentation.home.model

import android.content.Context

internal sealed class HomeAction {
    data object RetrieveDefaultIp: HomeAction()
    data class Connect(val ip: String, val isDefault: Boolean): HomeAction()
}