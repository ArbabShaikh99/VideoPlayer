package com.example.videplayer.UI_layer.Screen.Permission


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.videplayer.R
import com.example.videplayer.UI_layer.Screen.Home_AfterPermission.FolderScreenUI
import com.example.videplayer.UI_layer.Screen.Home_AfterPermission.VideoScreenUI
import com.example.videplayer.UI_layer.viewModel.VideoViewModel
import com.example.videplayer.ui.theme.lightGray
import com.example.videplayer.ui.theme.orange
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotAllowPermission(navController: NavController) {

    val videoViewModel: VideoViewModel = hiltViewModel()

    val tabsList = listOf(
        TabItems(
            name = "ARTISTS",
        ),
        TabItems(
            name = "ALBUMS",
        ),
        TabItems(
            name = "All VIDEOS"
        )
    )

    val pagerState = rememberPagerState(pageCount = { tabsList.size })
    val scope = rememberCoroutineScope()

    val scrollHeading = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    //          Scaffold contain 3 parameters
    Scaffold(

//                1. scrollig handle
        modifier = Modifier
            .nestedScroll(scrollHeading.nestedScrollConnection),

//              2. Background Color
        containerColor = lightGray,

        //              3. Top App Bar
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = lightGray
                ),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.trafficcone),
                            tint = Color.Unspecified,
                            contentDescription = "Video Player Icon",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Video Player",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Left
                    ) {
                        IconButton(onClick = { /* Handle search icon click */ }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(32.dp)

                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            )
        },
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGray)
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(23.dp))
            TabRow(
                containerColor = lightGray,
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier
                    .background(Color.Red)
                    .fillMaxWidth(),
                // indicator color define here----
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = orange,
                        height = 3.dp
                    )
                }
            ) {
                tabsList.forEachIndexed { index, tabItems ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        unselectedContentColor = Color.Black,
                        selectedContentColor = orange
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                tabItems.name,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Please Allow Permission to Access",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )

                    }

                    1 -> FolderScreenUI(videoViewModel, navController)
                    2 -> VideoScreenUI(videoViewModel, navController)
                }
//            }
            }
        }
    }
}

