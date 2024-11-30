package com.moneyfwd.features.profile.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.features.profile.R
import com.moneyfwd.features.profile.presentation.ProfileViewModel

@Composable
fun ProfileScreen(
    username: Username,
    viewModel: ProfileViewModel
) {
    val viewState = viewModel._viewState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(username) {
        viewModel.loadUserProfile(username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = { viewModel.onBackClick() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        // Profile Details
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            val profileDetails = viewState.profileDetails
            Image(
                painter = rememberAsyncImagePainter(
                    model = profileDetails.profilePictureUrl,
                    error = painterResource(R.drawable.avatar_placeholder)
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .placeholder(viewState.loading, highlight = PlaceholderHighlight.shimmer())
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .placeholder(
                    viewState.loading,
                    highlight = PlaceholderHighlight.shimmer()
                )
            ) {
                Text(text = profileDetails.fullName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "@${profileDetails.username}", modifier = Modifier.padding(vertical = 4.dp), fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = "Followers: ${profileDetails.followerCount} | Following: ${profileDetails.followingCount}",
                    fontSize = 14.sp
                )
            }
        }

        // Repository List
        Text(text = "Repositories", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
        LazyColumn {
            items(viewState.repositories) { repo ->
                RepositoryItemComposition(
                    repo = repo,
                    onClick = { openWebLink(context, repo.htmlUrl) })
            }
        }
    }
}

private fun openWebLink(context: Context, link: String) {
    if (link.isNotEmpty()) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}
