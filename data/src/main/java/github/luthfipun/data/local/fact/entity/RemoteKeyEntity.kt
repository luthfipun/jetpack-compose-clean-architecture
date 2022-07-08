package github.luthfipun.data.local.fact.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fact: String,
    val nextPage: Int?,
    val prevPage: Int?
)