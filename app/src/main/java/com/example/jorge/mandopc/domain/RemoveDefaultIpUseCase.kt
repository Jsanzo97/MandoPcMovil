package com.example.jorge.mandopc.domain

import com.example.jorge.mandopc.data.local.dao.IpDao

internal interface RemoveDefaultIpUseCase {
    suspend operator fun invoke()
}

internal class RemoveDefaultIpUseCaseImpl(
    private val ipDao: IpDao
) : RemoveDefaultIpUseCase {

    override suspend fun invoke() {
        return ipDao.delete()
    }
}