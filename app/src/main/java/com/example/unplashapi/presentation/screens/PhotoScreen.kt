package com.example.unplashapi.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.unplashapi.domain.models.Post
import com.example.unplashapi.presentation.viewmodels.PostViewModel


@Composable
fun PhotoFeedScreen(
    viewModel: PostViewModel,
    onPostClickListener: (Post) -> Unit,
    onAvatarClickListener: (String) -> Unit,
    onProfileClickListener: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadPosts(1)
    }
    val currentPosts by viewModel.posts.collectAsState()

    Scaffold(
        topBar = {
        },
        bottomBar = {
            BottomBar(
                onProfileClick = onProfileClickListener
            )
        }
    ) {
        LazyColumn(Modifier.padding(it)) {
            items(currentPosts) { post ->
                PostItem(
                    currentPost = post,
                    changeFavourite = { post ->
                        viewModel.toggleFavourite(post.id)
                    },
                    onPostClickListener,
                    onAvatarClickListener
                )

            }
        }

    }
}

@Composable
fun PostItem(
    currentPost: Post,
    changeFavourite: (Post) -> Unit,
    onPostClickListener: (Post) -> Unit,
    onAvatarClickListener: (String) -> Unit
) {
    Box(
        Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(5))

    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = {
                            onAvatarClickListener(currentPost.authorUserName)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    currentPost.authorAvatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))

                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = currentPost.authorName,
                    modifier = Modifier.weight(1f),
                    fontSize = 13.sp
                )

                Text(text = currentPost.location ?: "", fontSize = 12.sp)
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    currentPost.imageUrl,
                    contentDescription = null,
                    placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .heightIn(max = 300.dp)
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = {
                                onPostClickListener(currentPost)
                            }
                        ),
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Spacer(Modifier.width(20.dp))
                    Icon(
                        imageVector = if (!currentPost.isFavourite) Icons.Outlined.BookmarkBorder else Icons.Default.Bookmark,
                        contentDescription = null,
                        Modifier
                            .size(30.dp)
                            .clickable(
                                enabled = true,
                                interactionSource = null,
                                indication = null,
                                onClick = {
                                    changeFavourite(currentPost)
                                }
                            )
                    )

                }
            }
        }
    }
}

//positionClick:(Int)-> Unit)
@Composable
fun BottomBar(onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .clickable {
                    onProfileClick()
                }
        ) {
            Icon(Icons.Default.Home, contentDescription = null, Modifier.size(40.dp))
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .clickable { }
        ) {
            Icon(Icons.Default.Search, contentDescription = null, Modifier.size(40.dp))
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .clickable {
                    onProfileClick()
                }
        ) {
            Icon(Icons.Default.Person, contentDescription = null, Modifier.size(40.dp))
        }
    }
}

@Preview
@Composable
fun PhotoFeedPreview() {
    Scaffold(
        bottomBar = {
            BottomBar(
                {}
            )
        }
    ) {
        PostItem(
            currentPost = Post(
                id = "sdfsfsdf",
                authorName = "test author",
                authorUserName = "test username",
                authorAvatar = "afjhsdfk",
                imageUrl = "",

                ),
            changeFavourite = {},
            {},
            {}
        )
    }

}
