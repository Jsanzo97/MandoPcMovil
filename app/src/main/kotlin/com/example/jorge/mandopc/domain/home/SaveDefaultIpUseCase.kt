package com.example.jorge.mandopc.domain.home

import com.example.jorge.mandopc.data.local.dao.IpDao
import com.example.jorge.mandopc.data.local.model.IpModel

internal interface SaveDefaultIpUseCase {
    suspend operator fun invoke(ip: String)
}

internal class SaveDefaultIpUseCaseImpl(
    private val ipDao: IpDao
) : SaveDefaultIpUseCase {

    override suspend fun invoke(ip: String) {
        ipDao.delete()
        return ipDao.insert(IpModel(ip))
    }
}