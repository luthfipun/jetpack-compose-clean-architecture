package github.luthfipun.composemodularize.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.luthfipun.data.GatewayRepositoryImpl
import github.luthfipun.data.remote.fact.FactRepository
import github.luthfipun.data.remote.fact.FactRepositoryImpl
import github.luthfipun.domain.gateway.GatewayRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideGateway(gatewayRepositoryImpl: GatewayRepositoryImpl): GatewayRepository =
        gatewayRepositoryImpl

    @Singleton
    @Provides
    fun provideFactRepository(factRepositoryImpl: FactRepositoryImpl): FactRepository =
        factRepositoryImpl
}