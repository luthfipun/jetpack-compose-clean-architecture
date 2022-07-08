package github.luthfipun.domain.usecase

import github.luthfipun.domain.gateway.GatewayRepository
import javax.inject.Inject

class GetFactUseCase @Inject constructor(
    private val gatewayRepository: GatewayRepository
) {
    operator fun invoke() = gatewayRepository.getFacts()
}