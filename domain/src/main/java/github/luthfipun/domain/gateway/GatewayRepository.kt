package github.luthfipun.domain.gateway

import androidx.paging.PagingData
import github.luthfipun.domain.entity.Fact
import kotlinx.coroutines.flow.Flow

interface GatewayRepository {
    fun getFacts(): Flow<PagingData<Fact>>
}