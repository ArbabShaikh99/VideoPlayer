package com.example.videplayer.UI_layer.Screen.Home_AfterPermission

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.example.util.formatDate
import com.example.util.formatDuration
import com.example.util.formatFileSize
import com.example.videplayer.UI_layer.Navigation.PlayerScreenRoute
import com.example.videplayer.UI_layer.viewModel.VideoViewModel


@Composable
fun VideoScreenUI(
    videoViewModel: VideoViewModel,
    navController: NavController
){

   val AllVideos = videoViewModel.getAllVideos.collectAsState()
    val context = LocalContext.current
    val videoList = AllVideos.value.data


    when{
       AllVideos.value.isLoading ->{
            CircularProgressIndicator()
        }
        AllVideos.value.data.isNotEmpty() ->{
            LazyColumn (modifier = Modifier.fillMaxSize(),
              //  verticalArrangement = Arrangement.spacedBy(16.dp)
            )
            {
                items(videoList){ video->
                  Spacer(modifier = Modifier.height(20.dp))
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

       AllVideos.value.error != null -> {
       // Toast.makeText(context, AllVideos.value.error, Toast.LENGTH_SHORT).show()
         Log.d("error","${AllVideos.value.error}")
    }

 }

}

@Composable
fun  VideoCard(
    path: String,
    title: String,
    size: String,
    duration: String,
    dateAdded: String,
    fileNames: String,
    thumbnail: String,
    id: String,
    navController: NavController
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
              navController.navigate(PlayerScreenRoute(videoUri = path ,title =  title))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnail)
                    .videoFrameMillis(1000)
                    .build(),
                contentDescription = "Video thumbnail",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                imageLoader = imageLoader
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title.takeIf { it.isNotBlank() } ?: "Untitled",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                   text = "Duration: ${formatDuration(duration.toLongOrNull() ?: 0)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Size: ${formatFileSize(size.toLongOrNull() ?: 0)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Added: ${formatDate(dateAdded.toLongOrNull() ?: 0)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
