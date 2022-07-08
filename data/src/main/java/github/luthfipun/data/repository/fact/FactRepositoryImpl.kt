package github.luthfipun.data.repository.fact

import androidx.paging.*
import github.luthfipun.data.local.AppDatabase
import github.luthfipun.data.local.fact.entity.toFact
import github.luthfipun.data.paging.FactRemoteMediator
import github.luthfipun.data.remote.RemoteService
import github.luthfipun.domain.entity.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val remoteService: RemoteService,
    private val appDatabase: AppDatabase
) : FactRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getFact(): Flow<PagingData<Fact>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = FactRemoteMediator(remoteService, appDatabase)
        ){
            appDatabase.factDao().pageSource()
        }.flow.map { factEntity ->
            factEntity.map { it.toFact() }
        }
    }
}