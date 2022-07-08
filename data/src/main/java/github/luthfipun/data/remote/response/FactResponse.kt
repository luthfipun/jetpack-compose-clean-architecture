package github.luthfipun.data.remote.response

import com.google.gson.annotations.SerializedName
import github.luthfipun.data.local.fact.entity.FactEntity

data class FactResponse(
    @SerializedName("next_page_url")
    val nextPageUrl: String,
    @SerializedName("prev_page_url")
    val prevPageUrl: String,
    val data: List<FactDataResponse>?
){
    data class FactDataResponse(
        val fact: String
    )
}

fun FactResponse.FactDataResponse.toFactEntity(): FactEntity {
    return FactEntity(fact = fact)
}
