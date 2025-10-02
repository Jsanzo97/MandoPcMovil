package com.example.jorge.mandopc.domain

import com.example.jorge.mandopc.data.local.dao.IpDao

internal interface GetStoredDefaultIpUseCase {
    suspend operator fun invoke(): String
}

internal class GetStoredDefaultIpUseCaseImpl(
    private val ipDao: IpDao
) : GetStoredDefaultIpUseCase {

    override suspend fun invoke(): String {
        return ipDao.get()?.ip ?: "" // Safely handle null case
    }
}
