package com.example.videplayer.Domain_layer

import android.app.Application
import com.example.State.MainVideoAppState
import com.example.videplayer.Data_layer.VideoFileModule
import kotlinx.coroutines.flow.Flow

interface VideAppRepo {

    suspend fun  getAllVideo (application:Application): Flow<MainVideoAppState<List<VideoFileModule>>>

    suspend fun getVideoByFolder(application:Application):Flow<MainVideoAppState<Map<String, List<VideoFileModule>>>>

    suspend fun getAllVideosFolder(application: Application) : Flow<MainVideoAppState<List<Map<String,String>?>>>

}