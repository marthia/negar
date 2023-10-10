package me.marthia.negar.framework.presentation.note.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.marthia.negar.business.domain.model.dto.DiaryDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(notesViewModel: DiaryDetailViewModel = hiltViewModel()) {

    val diary by notesViewModel.noteItem.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "") }, actions = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Rounded.Star, contentDescription = "Star")
                }
            })
        }
    ) {
        diary?.let { item ->
            NotesContent(modifier = Modifier.padding(it), item = item)
        }
    }

}


@Composable
fun NotesContent(modifier: Modifier, item: DiaryDto) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier)
    ) {
        Text(text = item.title, style = MaterialTheme.typography.titleSmall)
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        Text(
            text = item.textContent,
            maxLines = 3,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis
        )
    }
}


