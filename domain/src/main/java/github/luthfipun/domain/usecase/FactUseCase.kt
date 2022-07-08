package github.luthfipun.domain.usecase

import javax.inject.Inject

data class FactUseCase @Inject constructor(
    val getFactUseCase: GetFactUseCase
)
