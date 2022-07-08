package github.luthfipun.data.local.fact.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import github.luthfipun.data.local.fact.entity.FactEntity

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(facts: List<FactEntity>)

    @Query("SELECT * FROM cat_fact")
    fun pageSource(): PagingSource<Int, FactEntity>

    @Query("DELETE FROM cat_fact")
    suspend fun clearAll()
}