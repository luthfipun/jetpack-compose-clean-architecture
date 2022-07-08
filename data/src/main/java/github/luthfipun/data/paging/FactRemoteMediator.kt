package github.luthfipun.data.paging

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import github.luthfipun.data.local.AppDatabase
import github.luthfipun.data.local.fact.entity.FactEntity
import github.luthfipun.data.local.fact.entity.RemoteKeyEntity
import github.luthfipun.data.remote.RemoteService
import github.luthfipun.data.remote.response.toFactEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FactRemoteMediator @Inject constructor(
    private val remoteService: RemoteService,
    private val appDatabase: AppDatabase
): RemoteMediator<Int, FactEntity>() {

    private val factDao = appDatabase.factDao()
    private val remoteKeyDao = appDatabase.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FactEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextPage
                }
            }

            val response = remoteService.getFacts(10, loadKey)
            var endOfPaginationReached = false

            if (response.isSuccessful){
                val body = response.body()
                val data = body?.data
                endOfPaginationReached = data?.isEmpty() == true
                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH){
                        factDao.clearAll()
                        remoteKeyDao.clearAll()
                    }

                    var nextPage: Int? = null
                    var prevPage: Int? = null

                    body?.let { responseBody ->
                        nextPage = prevNextUrlToInt(responseBody.nextPageUrl)
                        prevPage = prevNextUrlToInt(responseBody.prevPageUrl)
                    }

                    val keys = data?.map { factDataResponse ->
                        RemoteKeyEntity(
                            fact = factDataResponse.fact,
                            nextPage = nextPage,
                            prevPage = prevPage
                        )
                    }

                    remoteKeyDao.insertAll(keys ?: listOf())
                    factDao.insertAll(data?.map { it.toFactEntity() } ?: listOf())
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception){
            Log.e("ENOG", "MSG => ${e.localizedMessage}")
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FactEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                remoteKeyDao.getNextPageFact(it.fact)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FactEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                remoteKeyDao.getNextPageFact(it.fact)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, FactEntity>): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.fact?.let { factString ->
                remoteKeyDao.getNextPageFact(factString)
            }
        }
    }

    private fun prevNextUrlToInt(url: String?): Int? {
        if (url == null) return null
        val uri = Uri.parse(url)
        return uri.getQueryParameter("page")?.toInt()
    }
}