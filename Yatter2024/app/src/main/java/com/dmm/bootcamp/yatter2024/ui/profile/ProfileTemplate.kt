package com.dmm.bootcamp.yatter2024.ui.profile

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.dmm.bootcamp.yatter2024.domain.model.Status
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileTemplate(
    profileBindingModel: ProfileBindingModel,
    statusList: List<StatusBindingModel>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickPost: () -> Unit,
    onClickLogout: () -> Unit,
    onClickFollow: () -> Unit,
    onClickFollower: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = profileBindingModel.username)
                },
                navigationIcon = {
                    IconButton(onClick = onClickLogout){
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ExitToApp,
                            contentDescription = "logout"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickPost) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "post"
                )
                
            }
        }
    ) {paddingValues ->
        val pullRefershState = rememberPullRefreshState(isRefreshing, onRefresh)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefershState),
            contentAlignment = Alignment.Center,
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
            ){
                // Header
                Row{
                    //Icon
                    Column{
                        Text(profileBindingModel.username)
                        Text("id") // idを入れたい．Identifier→String
                    }
                }
                // bio
                Row{
                    TextButton(onClick = onClickFollow) {
                        Text("${profileBindingModel.numFollow} Follow")
                    }
                    TextButton(onClick = onClickFollower) {
                        Text("${profileBindingModel.numFollower} Follower")
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(statusList){item ->
                        StatusRow(item)
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefershState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            if(isLoading) {
                CircularProgressIndicator()
            }
        }

    }
}

@Preview
@Composable
private fun ProfileTemplatePreview() {
    Yatter2024Theme {
        Surface {
            ProfileTemplate(
                profileBindingModel = ProfileBindingModel(
                    username = "User",
                    numPost = 100,
                    numFollower = 101,
                    numFollow = 17,
                ),
                statusList = listOf(
                    StatusBindingModel(
                        id = "id1",
                        displayName = "display name1",
                        username = "username1",
                        avatar = null,
                        content = "preview content1",
                        attachmentMediaList = listOf()
                    ),
                    StatusBindingModel(
                        id = "id1",
                        displayName = "display name1",
                        username = "username1",
                        avatar = null,
                        content = "preview content2",
                        attachmentMediaList = listOf()
                    ),
                ),
                isLoading = false,
                isRefreshing = false,
                onRefresh = {},
                onClickPost = {},
                onClickLogout = {},
                onClickFollow = {},
                onClickFollower = {},
            )
        }
    }
}