package com.example.videplayer.Data_layer


data class VideoFileModule (
    val id :String?,
    var path :String,
    var title :String?,
    var fileName :String,
    var size :String,
    var duration: String,
    val dateAdded :String,
    var thumbnail :String? = null
 )