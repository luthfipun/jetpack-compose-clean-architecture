package github.luthfipun.data.local.fact.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import github.luthfipun.data.local.fact.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKeyEntity: RemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeyEntities: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_key WHERE fact LIKE :fact")
    suspend fun getNextPageFact(fact: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_key")
    suspend fun clearAll()
}