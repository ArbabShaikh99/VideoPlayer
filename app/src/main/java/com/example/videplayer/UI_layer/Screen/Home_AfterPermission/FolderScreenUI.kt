package com.example.videplayer.UI_layer.Screen.Home_AfterPermission

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.videplayer.UI_layer.Navigation.VideoByFolderRoute
import com.example.videplayer.UI_layer.viewModel.VideoViewModel

@Composable
fun FolderScreenUI(
    videoViewModel: VideoViewModel,
    navController: NavController
){

    val  AllFolderState =  videoViewModel.getVideoByFolderState.collectAsState()
    val FolderList = AllFolderState.value.data ?: emptyMap()




    val context = LocalContext.current

    when{
        AllFolderState.value.isLoading ->{
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        AllFolderState.value.data.isNotEmpty() ->{
            Log.d("issue","DATA --- ${AllFolderState.value.data}")

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),

            ) {
                items(FolderList.entries.toList()) { (folderName, videos) ->
                    Log.d("FolderScreen", "Rendering folder: $folderName with ${videos.size} videos")

                    FolderCard(
                        folderName = folderName,
                        videoCount = videos.size,
                        navController = navController
                    )
                }
            }
        }
        AllFolderState.value.error !=null->{
         Log.d("issue","Error --- ${AllFolderState.value.error}")
           // Toast.makeText(context, AllFolderState.value.error, Toast.LENGTH_SHORT).show()

        }
    }

}



@Composable
fun FolderCard(
    folderName: String,
    videoCount: Int,
   // map: Map<String, String>?,
    navController: NavController
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
              navController.navigate(VideoByFolderRoute(folderName))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Folder,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = folderName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${videoCount} videos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}