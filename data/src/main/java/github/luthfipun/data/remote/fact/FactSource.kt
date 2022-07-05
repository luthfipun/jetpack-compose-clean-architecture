package github.luthfipun.data.remote.fact

import androidx.paging.PagingSource
import androidx.paging.PagingState
import github.luthfipun.data.remote.RemoteService
import github.luthfipun.domain.entity.Fact
import retrofit2.HttpException
import java.io.IOException

class FactSource(
    private val remoteService: RemoteService
): PagingSource<Int, Fact>(){
    override fun getRefreshKey(state: PagingState<Int, Fact>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Fact> {
        return try {
            val nextPage = params.key ?: 1
            val pageSize = 20

            val factResponse = remoteService.getFacts(pageSize, nextPage)

            if (!factResponse.isSuccessful){
                return LoadResult.Error(Exception("Internal Server Error!"))
            }

            LoadResult.Page(
                data = factResponse.body()?.data ?: listOf(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = factResponse.body()?.currentPage?.plus(1)
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}