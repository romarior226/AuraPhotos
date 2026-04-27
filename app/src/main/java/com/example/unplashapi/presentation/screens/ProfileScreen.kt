package com.example.unplashapi.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.unplashapi.domain.models.SimplePhoto
import com.example.unplashapi.domain.models.UserDetail
import com.example.unplashapi.presentation.viewmodels.EditProfileUiState
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    profile: UserDetail,
    collection: List<SimplePhoto>,
    onBackPressed: () -> Unit,
    loadMorePhoto: (Int) -> Unit,
    onPhotoClicked: (String) -> Unit,
    isEditable: Boolean = false,
    onSaveProfile: (String, String, String, String, String, String, String) -> Unit,
    editState: EditProfileUiState
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
            val openAlertDialog = remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(profile.name, fontSize = 24.sp)
                    Text("@${profile.username}", fontSize = 14.sp)
                    Spacer(Modifier.height(5.dp))
                    if (isEditable) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(30)
                                )
                                .padding(vertical = 2.dp, horizontal = 5.dp)
                                .clickable {
                                    openAlertDialog.value = true
                                }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "")
                            Text("Edit profile")
                        }

                    }

                }
            }


            when {
                openAlertDialog.value -> {
                    MinimalDialog(
                        onDismissRequest = { openAlertDialog.value = false },
                        currentUser = editState,
                        onSaveProfile
                    )
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {
                if (profile.bio != null) Text(
                    profile.bio,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(20)
                        )
                        .padding(10.dp), lineHeight = 16.sp
                )
            }
            if (profile.location != null) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                    Text(profile.location, fontSize = 14.sp)
                }
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
        {},
        isEditable = TODO(),
        onSaveProfile = TODO(),
        editState = TODO(),
    )
    MinimalDialog(
        {},
        onSaveProfile = { string, string1, string2, string3, string4, string5, string6 ->
        },
        currentUser = TODO(),
    )
}

@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
    currentUser: EditProfileUiState,
    onSaveProfile: (String, String, String, String, String, String, String) -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        var editProfileUiState by remember { mutableStateOf(EditProfileUiState()) }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(620.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {

            Column() {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Change your data",
                        Modifier
                            .weight(1f)
                            .padding(top = 10.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = {
                                onDismissRequest()
                            }
                        ))
                }
                Column(
                    Modifier
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomTextFiled(
                        label = "Change username",
                        value = currentUser.username
                    ) {
                        editProfileUiState = editProfileUiState.copy(username = it)
                    }
                    CustomTextFiled(
                        label = "Change firstname",
                        value = currentUser.firstName
                    ) {
                        editProfileUiState = editProfileUiState.copy(firstName = it)
                    }
                    CustomTextFiled(
                        label = "Change lastname",
                        value = currentUser.lastName
                    ) {
                        editProfileUiState = editProfileUiState.copy(lastName = it)
                    }
                    CustomTextFiled(
                        label = "Change Email",
                        value = currentUser.email
                    ) {
                        editProfileUiState = editProfileUiState.copy(email = it)
                    }
                    CustomTextFiled(
                        label = "Change Location",
                        value = currentUser.location
                    ) {
                        editProfileUiState = editProfileUiState.copy(location = it)
                    }
                    CustomTextFiled(
                        label = "Change Bio",
                        value = currentUser.bio
                    ) {
                        editProfileUiState = editProfileUiState.copy(bio = it)
                    }
                    CustomTextFiled(
                        label = "Change Instagram",
                        value = currentUser.instagramUsername
                    ) {
                        editProfileUiState = editProfileUiState.copy(instagramUsername = it)
                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    OutlinedButton(
                        onClick = {
                            if (editProfileUiState.isDataValid) {
                                onSaveProfile(
                                    editProfileUiState.username,
                                    editProfileUiState.firstName,
                                    editProfileUiState.lastName,
                                    editProfileUiState.email,
                                    editProfileUiState.location,
                                    editProfileUiState.bio,
                                    editProfileUiState.instagramUsername
                                )
                                onDismissRequest()
                            }

                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

/*
 username = _state.value.username,
                firstName = _state.value.firstName,
                lastName = _state.value.lastName,
                email = _state.value.email,
                location = _state.value.location,
                bio = _state.value.bio,
                instagramUsername = _state.value.instagramUsername,
 */

@Composable
fun CustomTextFiled(label: String, value: String, onSaveClickListener: (String) -> Unit) {

    var currentText by remember { mutableStateOf(value) }
    var currentIcon by remember { mutableStateOf(Icons.Default.Save) }
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background, RoundedCornerShape(30))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            label = { Text(label) },
            value = currentText,
            onValueChange = {
                currentIcon = Icons.Default.Save
                currentText = it
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(10.dp))
        Icon(
            currentIcon, contentDescription = null, Modifier
                .size(20.dp)
                .clickable(
                    onClick = {
                        currentIcon = Icons.Default.Check
                        onSaveClickListener(currentText)
                    }
                ))
    }

}