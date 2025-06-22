package com.example.hudoverlay.core.di

import com.example.hudoverlay.data.repository.CarLocationRepositoryImpl
import com.example.hudoverlay.domain.repository.CarLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 리포지토리 구현체를 인터페이스에 바인딩하는 Hilt 모듈
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    /**
     * CarLocationRepository 인터페이스의 구현체를 제공
     * @param impl 실제 구현체인 CarLocationRepositoryImpl 인스턴스
     * @return CarLocationRepository 타입으로 사용 가능한 인스턴스
     */
    @Binds
    @Singleton
    abstract fun bindCarLocationRepository(
        impl: CarLocationRepositoryImpl
    ): CarLocationRepository
}
