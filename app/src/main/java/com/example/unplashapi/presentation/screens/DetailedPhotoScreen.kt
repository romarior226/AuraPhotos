package com.example.unplashapi.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.unplashapi.domain.models.DetailPhoto
import com.example.unplashapi.domain.models.PhotoStatistics
import com.example.unplashapi.domain.models.SimplePhoto
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailedScreen(
    detailPhoto: DetailPhoto,
    onBackPressed: () -> Unit,
    collection: List<SimplePhoto>,
    photoStatistics: PhotoStatistics,
    onAvatarClickListener: (String) -> Unit,
    loadMorePhoto: (Int) -> Unit,
    onPhotoClicked: (String) -> Unit,
) {
    val listState = rememberLazyStaggeredGridState()
    val currentPage = remember { mutableIntStateOf(1) }
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()

            lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 2
        }
    }
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            delay(500)
            loadMorePhoto(++currentPage.intValue)

        }
    }
    val clipboardManager = LocalClipboardManager.current
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(top = 30.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30))
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AsyncImage(
                        model = detailPhoto.authorAvatar,
                        contentDescription = null,
                        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(50))
                            .clickable(
                                onClick = {
                                    onAvatarClickListener(detailPhoto.authorUserName)
                                }
                            )

                    )
                    Text(
                        text = detailPhoto.authorName,
                        modifier = Modifier.weight(1f),
                        fontSize = 15.sp
                    )
                    Icon(Icons.Default.Add, contentDescription = null, Modifier.size(30.dp))
                    Icon(
                        Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                }

            }
        }
    ) { paddingValues ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            state = listState,
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
        {
            item(span = StaggeredGridItemSpan.FullLine) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = detailPhoto.urls,
                        contentDescription = null,
                        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .heightIn(max = 300.dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Spacer(Modifier.height(10.dp))
                    Stats(
                        downloads = detailPhoto.downloads ?: 0,
                        viewsCount = photoStatistics.views
                    ) {
                        clipboardManager.setText(AnnotatedString(detailPhoto.link))
                    }
                    Spacer(Modifier.height(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp)

                    ) {
                        if (detailPhoto.createdAt.isNotBlank()) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = detailPhoto.createdAt
                                    .replace("T", " ")
                                    .replace("Z", ""),
                                fontSize = 12.sp
                            )
                        }

                    }
                    Spacer(Modifier.height(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp)
                    ) {
                        if (!detailPhoto.location.isNullOrBlank()) {
                            Icon(
                                Icons.Default.PhotoCamera,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Text(detailPhoto.location, fontSize = 12.sp)
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Tags(tags = detailPhoto.tags)
                    Spacer(Modifier.height(10.dp))
                }
            }
            items(items = collection, key = { photo -> photo.id }) { photo ->
                AsyncImage(
                    model = photo.urls,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentHeight()
                        .widthIn(max = 180.dp)
                        .clickable { onPhotoClicked(photo.id) }
                )
            }

        }
    }
}


@Composable
fun Tags(tags: List<String>) {
    Text(
        "#Tags", Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
    )
    Spacer(Modifier.height(10.dp))
    FlowRow(
        modifier = Modifier
            .padding(horizontal = 0.dp)
            .fillMaxWidth()
    ) {
        tags.forEach { tags ->
            Row(modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)) {
                Text(
                    tags,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.surfaceDim,
                            shape = RoundedCornerShape(30)
                        )
                        .padding(vertical = 5.dp, horizontal = 8.dp),
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Composable
fun Stats(viewsCount: Int, downloads: Int, onShareClickListener: () -> Unit) {
    Row(
        Modifier
            .padding(vertical = 0.dp, horizontal = 0.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Download,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(converter(downloads))
        }
        Spacer(Modifier.width(20.dp))
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.RemoveRedEye,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.width(4.dp))
            Text(converter(viewsCount))
        }
        Icon(
            Icons.Default.Share,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable(
                interactionSource = null,
                onClick = {
                    onShareClickListener()
                }
            ),
        )
        Spacer(Modifier.width(15.dp))
        Icon(
            Icons.Default.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable(
                interactionSource = null,
                onClick = {}
            ),
        )
        Spacer(Modifier.width(15.dp))
        Icon(
            Icons.Default.MoreHoriz,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,

            modifier = Modifier.clickable(
                interactionSource = null,
                onClick = {}
            ),
        )


    }
}

fun converter(count: Int): String {
    return if (count < 1000) count.toString()
    else if (count < 1000000) "%.1f".format(count.toFloat() / 1_000) +"K"
    else "%.1f".format(count.toFloat() / 1_000_000) + "M"
}

@Preview
@Composable
fun DetailedScreenPreview() {
    DetailedScreen(
        detailPhoto = DetailPhoto(
            id = "testID",
            createdAt = "At 10",
            downloads = 1000,
            urls = "Urls",
            location = "location",
            authorName = "name",
            authorUserName = "userName",
            authorAvatar = "avatar",
            tags = listOf(
                "tesdgt1",
                "testdfgdfg2",
                "tesgt3",
                "tesgt1",
                "asdasdtest2",
                "tesgdt3",
                "tedfgst1",
                "tesasdasdt2",
                "tesdgdt3",
                "tesadadt1",
                "test2",
                "test3",
                "tedfgssfgt1",
                "test2",
                "tedfgst3"
            ),
            link = "",
        ),
        onBackPressed = {},
        loadMorePhoto = {},
        onPhotoClicked = {},
        onAvatarClickListener = {},
        collection = emptyList(),
        photoStatistics = PhotoStatistics(
            id = "sdfgjh",
            downloads = 12312,
            views = 34534534,
            changeViews = 123,
            changeDownloads = 4,
            valuesViews = listOf(),
            valuesDownloads = listOf()
        ),
    )
}