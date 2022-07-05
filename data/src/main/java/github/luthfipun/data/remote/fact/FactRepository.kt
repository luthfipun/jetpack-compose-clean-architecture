package github.luthfipun.data.remote.fact

import androidx.paging.PagingData
import github.luthfipun.domain.entity.Fact
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    suspend fun getFacts(): Flow<PagingData<Fact>>
}