package github.luthfipun.data.remote.fact.response

import com.google.gson.annotations.SerializedName
import github.luthfipun.domain.entity.Fact

data class FactResponse(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("total")
    val totalPage: Int,
    @SerializedName("data")
    val data: List<Fact>
)
