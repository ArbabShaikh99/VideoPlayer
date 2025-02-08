package com.example.videplayer.UI_layer.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.State.MainVideoAppState
import com.example.videplayer.Data_layer.VideoFileModule
import com.example.videplayer.Domain_layer.VideAppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideoViewModel @Inject constructor(val application :Application, private  val repo : VideAppRepo):
    ViewModel() {

    val showUi = MutableStateFlow(false)


    private val _getAllVideos = MutableStateFlow(AllVideoState())
    val getAllVideos = _getAllVideos.asStateFlow()

    private val _getAllVideoFolders = MutableStateFlow(VideoFolderState())
    val getAllVideoFolders = _getAllVideoFolders.asStateFlow()

    private val _getVideoByFolderState = MutableStateFlow(VideoByFolderState())
    val getVideoByFolderState = _getVideoByFolderState.asStateFlow()


    init {
        viewModelScope.launch {
            loadAllVideos()
            getAllFolder()
            getVideoByFolder()
        }
    }

    fun loadAllVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllVideo(application).collectLatest {
                when (it) {
                    is MainVideoAppState.loading -> {
                        _getAllVideos.value = AllVideoState(isLoading = true)
                    }

                    is MainVideoAppState.Success -> {
                        _getAllVideos.value = AllVideoState(data = it.data)
                    }

                    is MainVideoAppState.Error -> {
                        _getAllVideos.value = AllVideoState(
                            error = it.exception.toString()
                        )
                    }
                }
            }
        }
    }

    fun getAllFolder() {
        viewModelScope.launch (Dispatchers.IO){
            repo.getAllVideosFolder(application).collectLatest {
                when (it) {
                    is MainVideoAppState.loading -> {
                        _getAllVideoFolders.value = VideoFolderState(isLoading = true)
                    }

                    is MainVideoAppState.Success -> {
                        _getAllVideoFolders.value =
                            VideoFolderState(data = it.data)
                    }

                    is MainVideoAppState.Error -> {
                        _getAllVideoFolders.value =
                            VideoFolderState(error = it.exception.toString())
                    }

                }
            }
        }
    }

     fun getVideoByFolder(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getVideoByFolder(application).collectLatest {
                when(it){
                    is MainVideoAppState.loading->{
                        _getVideoByFolderState.value = VideoByFolderState(
                            isLoading = true
                        )
                    }
                    is MainVideoAppState.Success->{
                        _getVideoByFolderState.value = VideoByFolderState(
                            data = it.data
                        )
                    }
                    is MainVideoAppState.Error-> {
                        _getVideoByFolderState.value = VideoByFolderState(
                            error = it.exception.toString()
                        )
                    }
                }
            }
        }
    }
}

data class AllVideoState(
    val isLoading: Boolean = false,
    val data: List<VideoFileModule> = emptyList(),
    val error: String?= null
)

data class VideoFolderState(
    val isLoading: Boolean = false,
    val data: List<Map<String, String>?> = emptyList(),
    val error: String? = null
)

data class VideoByFolderState(
    val isLoading: Boolean = false,
    val data: Map<String, List<VideoFileModule>> = emptyMap(),
    val error: String? = null
)