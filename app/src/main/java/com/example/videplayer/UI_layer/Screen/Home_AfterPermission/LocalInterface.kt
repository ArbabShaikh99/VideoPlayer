package com.example.videplayer.UI_layer.Screen.Home_AfterPermission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.videplayer.R
import com.example.videplayer.UI_layer.Screen.Component.LocalList
import com.example.videplayer.ui.theme.lightGray
import com.example.videplayer.ui.theme.orange

@Composable
fun LocalInterface() {
    val localDataFor_A = LocalList.itemsLit_A
    val localDataFor_B = LocalList.itemsList_B

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        // Section A
        Text(
            "A",
            color = orange,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
                //.weight(1f) // 🔹 Let grid take remaining space dynamically
        ) {
            items(localDataFor_A) {
                AlbumCard(
                    Image = it.image,
                    title = it.SingerName,
                    subtitle = it.Album,
                    onClick = {}
                )
            }
        }

        // Section B with Text & Icon at Correct Position
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), // 🔹 Added vertical padding for spacing
            horizontalArrangement = Arrangement.SpaceBetween, // Text left & Box right
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "B",
                color = orange,
                fontSize = 30.sp,
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(orange)
                    .clickable { /* Handle Click */ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Shuffle, // Shuffle Icon
                    contentDescription = "Shuffle",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(455.dp)
            // .weight(1f) // 🔹 Let grid take remaining space dynamically
        ) {
            items(localDataFor_B) {
                AlbumCard(
                    Image = it.image,
                    title = it.SingerName,
                    subtitle = it.Album,
                    onClick = {}
                )
            }
        }
    }
}





    @Composable
    fun AlbumCard(
        Image: Int,
        title: String,
        subtitle: String,
        onClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .height(232.dp)
                .width(180.dp)
                .padding(7.dp)
                //.background(lightGray)
                .clickable {
                    onClick()
                }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(lightGray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(170.dp)
                ) {
                    Image(
                        painter = painterResource(id = Image),
                        contentDescription = "Book Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                    )

                    // Play Icon (Centered)
                    Icon(
                        painter = painterResource(R.drawable.baseline_play_circle_filled_24), // Replace with your play icon
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomEnd) // Centered in Box
                            .size(55.dp)
                            // .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape) // Optional: Add a semi-transparent background
                            .padding(8.dp) // Adjust padding for better look
                    )
                }

                // Spacer(modifier = Modifier.height(0.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, end = 8.dp, start = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Text(
                            text = subtitle,  // This will be "Album" or any subtitle
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black  // A slightly lighter color
                        )
                    }


                    Icon(
                        painter = painterResource(R.drawable.doticon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(35.dp)
                    )

                }
            }

        }
    }





