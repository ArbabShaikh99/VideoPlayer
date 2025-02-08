package com.example.videplayer.UI_layer.Navigation

import kotlinx.serialization.Serializable

@Serializable
object AllVideoScreenRoutes

@Serializable
object  HomeRotes

@Serializable
object UserPermissionRoute

@Serializable
data class PlayerScreenRoute(
    val videoUri :String,
    val title :String? =null
)

@Serializable
data class VideoByFolderRoute(
    val folderName : String
)