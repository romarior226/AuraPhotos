package com.example.unplashapi.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.unplashapi.domain.models.ResultState
import com.example.unplashapi.domain.models.SimplePhoto
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    searchState: ResultState<List<SimplePhoto>>,
    searchQuery: (String) -> Unit,
    loadMorePhoto: (Int) -> Unit,
    onPhotoClicked: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            Row(
                Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                    )
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var query by rememberSaveable { mutableStateOf("") }
                TextField(
                    onValueChange = {
                        query = it
                        searchQuery(it)
                    },
                    value = query,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(30))
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (searchState) {
                is ResultState.Error ->
                    Text(searchState.message)

                is ResultState.Success -> SearchedContent(
                    searchedPhotos = searchState.data,
                    onPhotoClicked,
                    loadMorePhoto
                )

                ResultState.Loading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }


        }
    }

}

@Composable
fun SearchedContent(
    searchedPhotos: List<SimplePhoto>,
    onPhotoClicked: (String) -> Unit,
    loadMorePhoto: (Int) -> Unit
) {
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
            loadMorePhoto(++currentPage.value)

        }
    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        state = listState,
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 10.dp),

        ) {
        items(searchedPhotos, key = { photo -> photo.id }) {
            AsyncImage(
                model = it.urls,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentHeight()
                    .widthIn(max = 180.dp)
                    .animateItem()
                    .clickable(
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
fun SceachScreenPreview() {
    SearchScreen(
        searchState = ResultState.Loading,
        searchQuery = {},
        loadMorePhoto = {},
        onPhotoClicked = {}
    )
}