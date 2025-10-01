package com.example.jorge.mandopc.domain

import com.example.jorge.mandopc.data.local.IpDbHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

internal interface RemoveDefaultIpUseCase {
    operator fun invoke()
}

internal class RemoveDefaultIpUseCaseImpl: RemoveDefaultIpUseCase, KoinComponent {
    private val ipDbHelper: IpDbHelper by inject()

    override fun invoke() {
        ipDbHelper.deleteIp("1")
    }

}