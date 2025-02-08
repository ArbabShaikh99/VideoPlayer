package com.example.videplayer.Data_layer

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import androidx.core.database.getStringOrNull
import com.example.State.MainVideoAppState
import com.example.videplayer.Domain_layer.VideAppRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class VideoAppRepoImpl: VideAppRepo {

    override suspend fun getAllVideo(application: Application): Flow<MainVideoAppState<List<VideoFileModule>>> =
        flow {
            try {
                emit(MainVideoAppState.loading)
                val allVideos = ArrayList<VideoFileModule>()

                val projection = arrayOf(
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.DATE_ADDED,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.DISPLAY_NAME
                )

                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

                val cursor = application.contentResolver.query(uri, projection, null, null, null)

                Log.d("VideoDebug", "Querying videos...") // Debug log added

                if (cursor != null) {
                    Log.d("VideoDebug", "Total videos found: ${cursor.count}") // Count check

                    while (cursor.moveToNext()) {
                        val id = cursor.getStringOrNull(0)
                        val path = cursor.getStringOrNull(1)
                        val title = cursor.getStringOrNull(2) ?: "Unknown Title"
                        val size = cursor.getStringOrNull(3) ?: "0"
                        val dateAdded = cursor.getStringOrNull(4) ?: "0"
                        val duration = cursor.getStringOrNull(5) ?: "0"
                        val fileName = cursor.getStringOrNull(6) ?: "Unknown File"

                        if (id != null && path != null) {  // Only process valid videos
                            val thumbnails = ContentUris.withAppendedId(
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                id.toLong()
                            )

                            val videoFile = VideoFileModule(
                                id = id,
                                path = path,
                                title = title,
                                size = size,
                                duration = duration,
                                fileName = fileName,
                                dateAdded = dateAdded,
                                thumbnail = thumbnails.toString()
                            )

                            allVideos.add(videoFile)
                            Log.d("VideoDebug", "Added Video: $title, Path: $path") // Debug log
                        } else {
                            Log.e("VideoDebug", "Skipped invalid video entry: ID or Path is null")
                        }
                    }
                } else {
                    Log.e("VideoDebug", "Cursor is null! Query failed.")
                }

                cursor?.close()
                emit(MainVideoAppState.Success(allVideos))

            } catch (e: Exception) {
                Log.e("VideoDebug", "Error fetching videos: ${e.message}")
                emit(MainVideoAppState.Error(e))
            }
        }




    override suspend fun getAllVideosFolder(application: Application): Flow<MainVideoAppState<List<Map<String, String>>>> =
        flow {
            emit(MainVideoAppState.loading)
            try {
                val folders = mutableMapOf<String, String>()

                val projection = arrayOf(MediaStore.Video.Media.DATA)
                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                val cursor = application.contentResolver.query(uri, projection, null, null, null)

                Log.d("FolderDebug", "Querying video folders...") // Debug log added

                cursor?.use {
                    val dataColumnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                    while (it.moveToNext()) {
                        val videoPath = it.getString(dataColumnIndex)
                        val file = File(videoPath)
                        val folderPath = file.parentFile?.absolutePath
                        val folderName = file.parentFile?.name

                        if (folderName != null && folderPath != null) {
                            folders[folderName] = folderPath
                            Log.d("FolderDebug", "Folder Found: $folderName, Path: $folderPath") // Debug log for each folder
                        }
                    }
                }

                Log.d("FolderDebug", "Total folders found: ${folders.size}") // Log total folders

                emit(
                    MainVideoAppState.Success(
                        folders.map { (folderName, folderPath) ->
                            mapOf(folderName to folderPath)
                        }
                    )
                )
            } catch (e: Exception) {
                Log.e("FolderDebug", "Error fetching folders: ${e.message}")
                emit(MainVideoAppState.Error(e))
            }
        }

    override suspend fun getVideoByFolder(application: Application): Flow<MainVideoAppState<Map<String, List<VideoFileModule>>>> =
        flow {
            emit(MainVideoAppState.loading)
            try {
                getAllVideo(application).collect { appState ->
                    if (appState is MainVideoAppState.Success) {
                        val allVideos = appState.data
                        val videoByFolder = allVideos.groupBy { video ->
                            File(video.path).parentFile?.name ?: "Unknown Folder"
                        }

                        Log.d("FolderDebug", "Videos grouped by folder: ${videoByFolder.keys}")

                        emit(MainVideoAppState.Success(videoByFolder))
                    } else if (appState is MainVideoAppState.Error) {
                        emit(appState)
                    }
                }

            } catch (e: Exception) {
                Log.e("FolderDebug", "Error grouping videos by folder: ${e.message}")
                emit(MainVideoAppState.Error(e))
            }
        }

//    override suspend fun getVideoByFolder(application: Application): Flow<MainVideoAppState<Map<String, List<VideoFileModule>>>> =
//        flow {
//
//            emit(MainVideoAppState.loading)
//
//            try {
//                getAllVideo(application).collect { appState ->
//                    if (appState is MainVideoAppState.Success) {
//                        val allVideos = appState.data
//                        val videoByFolder = allVideos.groupBy { video ->
//                            File(video.path).parentFile?.name ?: "Unknown Folder"
//
//                        }
//                        emit(MainVideoAppState.Success(videoByFolder))
//                    } else if (appState is MainVideoAppState.Error) {
//                        emit(appState)
//                    }
//                }
//
//            } catch (e: Exception) {
//                emit(MainVideoAppState.Error(e))
//            }
//        }
//
//    override suspend fun getAllVideosFolder(application: Application): Flow<MainVideoAppState<List<Map<String,String>>>> =
//        flow {
//
//            emit(MainVideoAppState.loading)
//            try {
//                val folders = mutableMapOf<String,String>()
//                val projection = arrayOf(
//                    MediaStore.Video.Media.DATA
//                )
//                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//                val cursor = application.contentResolver.query(uri, projection, null, null, null)
//
//                cursor?.use {
//                    val dataColumnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
//                    while (it.moveToNext()) {
//                        val videoPath = it.getString(dataColumnIndex)
//                        val file = File(videoPath)
//                        val folderPath = file.parentFile?.absolutePath
//                        val folderName = file.parentFile?.name
//                        if (folderName != null && folderPath != null) {
//                            folders[folderName] = folderPath
//                        }
//                    }
//                }
//                emit(
//                    MainVideoAppState.Success(
//                        folders.map { (folderName, folderPath) ->
//                            mapOf(folderName to folderPath)
//                        }
//                    )
//                )
//            } catch (e: Exception) {
//                emit(MainVideoAppState.Error(e))
//            }
//
//        }

}
