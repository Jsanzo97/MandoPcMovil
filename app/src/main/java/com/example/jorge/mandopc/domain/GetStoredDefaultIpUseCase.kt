package com.example.jorge.mandopc.domain

import com.example.jorge.mandopc.data.local.IpDbHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal interface GetStoredDefaultIpUseCase {
    operator fun invoke(): String
}

internal class GetStoredDefaultIpUseCaseImpl: GetStoredDefaultIpUseCase, KoinComponent {
    private val ipDbHelper: IpDbHelper by inject()

    override fun invoke(): String =
        ipDbHelper.readIp("1") ?: ""
}