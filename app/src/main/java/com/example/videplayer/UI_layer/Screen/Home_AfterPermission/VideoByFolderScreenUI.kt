package com.example.videplayer.UI_layer.Screen.Home_AfterPermission

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.videplayer.UI_layer.Screen.Component.CustemTopAppBar
import com.example.videplayer.UI_layer.viewModel.VideoViewModel

@Composable
 fun VideoByFolderScreenUI(
     folderName :String,
     videoViewModel: VideoViewModel,
     navController: NavController
 ) {

    val videoByFolderState = videoViewModel.getVideoByFolderState.collectAsState()
    val context = LocalContext.current

    when {
        videoByFolderState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        videoByFolderState.value.data.isNotEmpty() -> {
            Scaffold(
                topBar = { CustemTopAppBar(topAppBarText = folderName, navController = navController) }
            ) {innerPadding ->


              //  val videoFolders = videoViewModel.getVideoByFolderState.collectAsState()
                val videosInFolder = videoByFolderState.value.data[folderName] ?: emptyList()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(videosInFolder) { video ->
                        VideoCard(
                            path = video.path,
                            title = video.title ?: "Untitled",
                            size = video.size ?: "Unknown",
                            duration = video.duration ?: "Unknown",
                            dateAdded = video.dateAdded ?: "Unknown",
                            fileNames = video.fileName ?: "Unknown",
                            thumbnail = video.thumbnail ?: "Unknown",
                            id = video.id ?: "Unknown",
                            navController = navController

                        )
                    }
                }
            }

        }
        videoByFolderState.value.error != null -> {
            Toast.makeText(context, videoByFolderState.value.error, Toast.LENGTH_SHORT).show()
        }

    }



}