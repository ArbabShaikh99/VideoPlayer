package com.example.videplayer.UI_layer.Screen.Component

import androidx.compose.runtime.Composable
import com.example.videplayer.R

object LocalList {
    val itemsLit_A = listOf(
        Listof(
            image = R.drawable.so,
            SingerName = "Arjit Singh Song...",
            Album = "18 Album"
        ),
        Listof(
            image = R.drawable.ati,
            SingerName = "Atif Aslam Song...",
            Album = "24 Album"
        ),
        Listof(
            image = R.drawable.alizafar,
            SingerName = "Ali Zafar Song...",
            Album = "30 Album"
        ),
        Listof(
            image = R.drawable.marsh,
            SingerName = "Marshmello Song..",
            Album = "30 Album"
        )
    )

    val itemsList_B = listOf(
        Listof(
            image = R.drawable.badshah,
            SingerName = "Badshah Song...",
            Album = "25 Album"
        ),
        Listof(
            image = R.drawable.bilalsaeed,
            SingerName = "Bilal Saeed Song...",
            Album = "12 Album"
        ),
        Listof(
            image = R.drawable.bohemia,
            SingerName = "Bohemia Song...",
            Album = "20 Album"
        ),
        Listof(
            image = R.drawable.babul_supriyo,
            SingerName = "Babul Supriyo Song...",
            Album = "15 Album"
        )

    )

}



data  class Listof(
 val image :Int,
  val SingerName:String,
    val Album: String
 )