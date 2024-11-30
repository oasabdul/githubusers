package com.moneyfwd.features.profile.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.moneyfwd.domain.profile.model.Username
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
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            val profileDetails = viewState.profileDetails
            Image(
                painter = rememberAsyncImagePainter(profileDetails.profilePictureUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
                    .placeholder(viewState.loading, highlight = PlaceholderHighlight.shimmer()),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.placeholder(
                    viewState.loading,
                    highlight = PlaceholderHighlight.shimmer()
                )
            ) {
                Text(text = profileDetails.username, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = profileDetails.fullName, fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = "Followers: ${profileDetails.followerCount} | Following: ${profileDetails.followingCount}",
                    fontSize = 14.sp
                )
            }
        }

        // Repository List
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
