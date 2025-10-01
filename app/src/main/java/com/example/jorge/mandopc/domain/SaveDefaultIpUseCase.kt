package com.example.jorge.mandopc.domain

import com.example.jorge.mandopc.data.local.IpDbHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

internal interface SaveDefaultIpUseCase {
    operator fun invoke(ip: String)
}

internal class SaveDefaultIpUseCaseImpl: SaveDefaultIpUseCase, KoinComponent {
    private val ipDbHelper: IpDbHelper by inject()

    override fun invoke(ip: String) {
        ipDbHelper.insertIp(ip)
    }

}