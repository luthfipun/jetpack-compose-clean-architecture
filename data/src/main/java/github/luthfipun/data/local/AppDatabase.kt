package github.luthfipun.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import github.luthfipun.data.local.fact.dao.FactDao
import github.luthfipun.data.local.fact.dao.RemoteKeyDao
import github.luthfipun.data.local.fact.entity.FactEntity
import github.luthfipun.data.local.fact.entity.RemoteKeyEntity

@Database(entities = [
    FactEntity::class,
    RemoteKeyEntity::class
], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun factDao(): FactDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        const val DATABASE_NAME = "app_database_db"
    }
}