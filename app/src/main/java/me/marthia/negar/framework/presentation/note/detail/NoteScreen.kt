package me.marthia.negar.framework.presentation.note.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.marthia.negar.framework.presentation.note.NotesViewModel

@Composable
fun NoteScreen(id: Long, notesViewModel: NotesViewModel = hiltViewModel()) {
    var noteItem by remember { mutableStateOf(Note()) }
    LaunchedEffect(id) {
        noteItem = notesViewModel.getNote(id = id)
    }

    Scaffold {
        NotesContent(modifier = Modifier.padding(it), item = noteItem)
    }

}


@Composable
fun NotesContent(modifier: Modifier, item: Note) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = item.title, style = MaterialTheme.typography.titleSmall)
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        Text(
            text = item.body,
            maxLines = 3,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis
        )
    }
}


