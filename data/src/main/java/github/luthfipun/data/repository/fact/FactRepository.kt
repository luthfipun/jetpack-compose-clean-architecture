package github.luthfipun.data.repository.fact

import androidx.paging.PagingData
import github.luthfipun.domain.entity.Fact
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    fun getFact(): Flow<PagingData<Fact>>
}