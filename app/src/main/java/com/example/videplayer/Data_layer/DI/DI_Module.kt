package com.example.videplayer.Data_layer.DI

import com.example.videplayer.Data_layer.VideoAppRepoImpl
import com.example.videplayer.Domain_layer.VideAppRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI_Module {

    @Provides
    @Singleton
    fun provideRepo (): VideAppRepo {
        return  VideoAppRepoImpl()
    }


}