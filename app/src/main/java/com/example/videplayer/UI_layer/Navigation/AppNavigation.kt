package com.example.videplayer.UI_layer.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.videplayer.UI_layer.Screen.Home_AfterPermission.ExplayerScreen
import com.example.videplayer.UI_layer.Screen.Permission.HomeScreenUI
import com.example.videplayer.UI_layer.Screen.Permission.UserPermissionPoppup
import com.example.videplayer.UI_layer.Screen.Home_AfterPermission.VideoByFolderScreenUI
import com.example.videplayer.UI_layer.Screen.Home_AfterPermission.VideoScreenUI
import com.example.videplayer.UI_layer.viewModel.VideoViewModel
import com.example.videplayer.UI_layer.Navigation.AllVideoScreenRoutes as AllVideoScreenRoutes1

@Composable
fun  AppNavigation() {
    val navController = rememberNavController()

    val videoViewModel : VideoViewModel = hiltViewModel()

    NavHost(navController, startDestination = UserPermissionRoute) {

        composable<UserPermissionRoute>{
            UserPermissionPoppup(navController = navController)
        }

        composable<AllVideoScreenRoutes1> {
            VideoScreenUI(
                videoViewModel,
                navController
            )
        }
       composable<HomeRotes> {
           HomeScreenUI(navController)
       }

        composable<PlayerScreenRoute> {
            val videoUri : PlayerScreenRoute = it.toRoute<PlayerScreenRoute>()
            ExplayerScreen(videoUri.videoUri , videoUri.title)
        }
        composable<VideoByFolderRoute> {
            val backentry : VideoByFolderRoute = it.toRoute<VideoByFolderRoute>()

            VideoByFolderScreenUI(
                folderName = backentry.folderName,
                videoViewModel,
                navController
            )
        }
    }
}