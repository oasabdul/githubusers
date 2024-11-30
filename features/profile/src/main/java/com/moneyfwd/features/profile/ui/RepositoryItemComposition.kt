package com.moneyfwd.features.profile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyfwd.domain.profile.model.RepositoryItem

@Composable
fun RepositoryItemComposition(repo: RepositoryItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .wrapContentHeight()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = repo.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            if (repo.language.isNotEmpty()) {
                Text(text = "Language: ${repo.language}", fontSize = 14.sp, color = Color.Gray)
            }
            Text(text = "Stars: ${repo.stars}", fontSize = 14.sp, color = Color.Gray)
            if (repo.description.isNotEmpty()) {
                Text(text = repo.description, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}