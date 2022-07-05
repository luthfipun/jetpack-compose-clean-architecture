package github.luthfipun.domain.usecase

import androidx.paging.PagingData
import github.luthfipun.domain.entity.Fact
import github.luthfipun.domain.gateway.GatewayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FactUseCase @Inject constructor(
    private val gatewayRepository: GatewayRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Fact>> {
        return gatewayRepository.getFact()
    }
}