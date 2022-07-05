package github.luthfipun.data.remote.fact

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import github.luthfipun.data.remote.RemoteService
import github.luthfipun.domain.entity.Fact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val remoteService: RemoteService
) : FactRepository {
    override suspend fun getFacts(): Flow<PagingData<Fact>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                FactSource(remoteService)
            }
        ).flow
    }
}