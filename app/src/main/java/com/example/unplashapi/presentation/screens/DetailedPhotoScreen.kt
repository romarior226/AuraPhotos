package com.example.unplashapi.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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

@Composable
fun DetailedScreen(detailPhoto: DetailPhoto, onBackPressed: () -> Unit ,  onAvatarClickListener : (String)-> Unit) {
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
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
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
            Stats(downloads = detailPhoto.downloads ?: 0, viewsCount = 0) {
                clipboardManager.setText(AnnotatedString(detailPhoto.link))
            }
            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)

            ) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text("Published on:${detailPhoto.createdAt}", fontSize = 12.sp)
            }
            Spacer(Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                Icon(
                    Icons.Default.PhotoCamera,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text("${detailPhoto.location}", fontSize = 12.sp)
            }
            Spacer(Modifier.height(10.dp))
            Tags(tags = detailPhoto.tags)

        }
    }
}


@Composable
fun Tags(tags: List<String>) {
    Text(
        "#Tags", Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    )
    Spacer(Modifier.height(10.dp))
    FlowRow(
        horizontalArrangement = Arrangement.Absolute.spacedBy(0.dp),
        modifier = Modifier.padding(horizontal = 3.dp)
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
                        .padding(vertical = 6.dp, horizontal = 8.dp),
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
            .padding(vertical = 0.dp, horizontal = 15.dp)
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
            Text("$downloads")
        }
        Spacer(Modifier.width(20.dp))
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.RemoveRedEye,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text("$viewsCount")
        }
        Icon(
            Icons.Default.Share,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
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
                indication = null,
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
                indication = null,
                onClick = {}
            ),
        )


    }
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
        {}
    )
}