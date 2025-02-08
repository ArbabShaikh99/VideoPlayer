package com.example.videplayer.UI_layer.Screen.Permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.videplayer.UI_layer.viewModel.VideoViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.runtime.collectAsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UserPermissionPoppup(
    viewModel: VideoViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val mediaPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(permission = Manifest.permission.READ_MEDIA_VIDEO)
    } else {
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    val mediaPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission() ){
        if (it){
            viewModel.showUi.value = true
        }else
        {
            viewModel.showUi.value = false
        }
    }
    LaunchedEffect(key1 = mediaPermission){
        if (!mediaPermission.status.isGranted){
            mediaPermissionLauncher.launch(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_VIDEO
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
            )

        }else{
            viewModel.showUi.value = true
        }
    }

    val state  = viewModel.showUi.collectAsState()
    if (state.value){
        HomeScreenUI(navController = navController)
    }else{
        NotAllowPermission(navController)
    }
}
