package com.example.unplashapi.presentation.screens

import android.renderscript.Allocation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.unplashapi.domain.models.SimplePhoto
import com.example.unplashapi.domain.models.UserDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

@Composable
fun ProfileScreen(
    profile: UserDetail,
    collection: List<SimplePhoto>,
    onBackPressed: () -> Unit,
    loadMorePhoto: (Int) -> Unit,
    onPhotoClicked: (String) -> Unit
) {
    Scaffold(Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .padding(it)
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = profile.profileImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(shape = RoundedCornerShape(50))
                )
                Spacer(Modifier.width(10.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    modifier = Modifier.widthIn(max = 180.dp)
                ) {
                    Text(profile.name, fontSize = 24.sp)

                }
            }
            Box(Modifier.fillMaxWidth().padding(start = 20.dp)) {
                Text(profile.bio ?: "", fontSize = 14.sp, lineHeight = 12.sp)
            }
            Row(Modifier.fillMaxWidth().padding(start = 20.dp) , verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn , contentDescription = null)
                Text(profile.location ?: "", fontSize = 14.sp)
            }
            Collections(profile)
            PhotoGrid(list = collection, loadMorePhoto, onPhotoClicked)

        }
    }
}

@Composable
fun Collections(profile: UserDetail) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(30)
                    )
                    .padding(10.dp)
            ) {
                Icon(Icons.Default.Photo, contentDescription = null)
                Text("${profile.totalPhotos}")

            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(30)
                    )
                    .padding(10.dp)
            ) {
                Icon(Icons.Default.Collections, contentDescription = null)
                Text("${profile.totalCollections}")

            }
        }

    }
}

@Composable
fun PhotoGrid(list: List<SimplePhoto>, loadMore: (Int) -> Unit, onPhotoClicked: (String) -> Unit) {
    val listState = rememberLazyStaggeredGridState()
    val currentPage = remember { mutableStateOf(1) }
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()

            lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 2
        }
    }
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            delay(500)
            loadMore(++currentPage.value)

        }
    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        state = listState,
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 10.dp),

        ) {
        items(list) {
            AsyncImage(
                model = it.urls,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentHeight()
                    .widthIn(max = 180.dp)
                    .animateItem()
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = {
                            onPhotoClicked(it.id)
                        }
                    )
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        profile = UserDetail(
            id = "sfgsfg",
            username = "testUserName",
            profileImage = "Test",
            name = "name",
            bio = "bioasdfsdfsfsfsdfsdfsf",
            totalCollections = 10,
            location = "selo",
            totalPhotos = 1
        ),
        collection = listOf(),
        onBackPressed = { },
        loadMorePhoto = {},
        {}
    )
}