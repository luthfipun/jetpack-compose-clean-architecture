package github.luthfipun.data

import androidx.paging.PagingData
import github.luthfipun.data.remote.fact.FactRepository
import github.luthfipun.domain.entity.Fact
import github.luthfipun.domain.gateway.GatewayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GatewayRepositoryImpl @Inject constructor(
    private val factRepository: FactRepository
): GatewayRepository {
    override suspend fun getFact(): Flow<PagingData<Fact>> {
        return factRepository.getFacts()
    }
}