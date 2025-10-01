package com.example.jorge.mandopc.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jorge.mandopc.presentation.theme.AppBackground
import com.example.jorge.mandopc.presentation.theme.AppPrimary

@Composable
internal fun CustomToolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppPrimary),
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Mando Pc",
            color = AppBackground,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Develop by Jsanzo97®",
            color = AppBackground,
            modifier = Modifier.padding(16.dp)
        )
    }
}