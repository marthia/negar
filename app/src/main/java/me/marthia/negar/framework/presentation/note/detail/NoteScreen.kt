package me.marthia.negar.framework.presentation.note.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.marthia.negar.business.domain.model.dto.DiaryDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(navController: NavHostController = rememberNavController(), notesViewModel: DiaryDetailViewModel = hiltViewModel()) {

    val diary by notesViewModel.noteItem.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
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
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.title,
            style = MaterialTheme.typography.titleMedium.copy(textDirection = TextDirection.ContentOrRtl)
        )
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = item.textContent,
            style = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrRtl),
        )
    }
}


