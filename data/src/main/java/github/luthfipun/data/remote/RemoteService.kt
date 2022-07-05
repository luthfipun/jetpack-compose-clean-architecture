package github.luthfipun.data.remote

import github.luthfipun.data.remote.fact.response.FactResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("facts")
    suspend fun getFacts(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<FactResponse>
}