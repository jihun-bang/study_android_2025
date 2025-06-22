package com.example.websocket_map.drivers.di

import com.example.websocket_map.drivers.data.datasource.DriversWebSocketDataSource
import com.example.websocket_map.drivers.data.repository.DriversRepositoryImpl
import com.example.websocket_map.drivers.domain.repository.DriversRepository
import com.example.websocket_map.network.websocket.OkHttpWebSocketService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 드라이버 모듈의 의존성 주입을 위한 Dagger Hilt 모듈
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DriversModule {

    @Provides
    @Singleton
    fun providesDriversDataSource(webSocketService: OkHttpWebSocketService): DriversWebSocketDataSource =
        DriversWebSocketDataSource(webSocketService)

    @Provides
    @Singleton
    fun providesDriversRepository(dataSource: DriversWebSocketDataSource): DriversRepository =
        DriversRepositoryImpl(dataSource)
}
