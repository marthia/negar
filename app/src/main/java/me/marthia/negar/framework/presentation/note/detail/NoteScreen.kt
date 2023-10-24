package me.marthia.negar.framework.presentation.note.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.marthia.negar.R
import me.marthia.negar.business.domain.model.dto.DiaryDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavHostController = rememberNavController(),
    notesViewModel: DiaryDetailViewModel = hiltViewModel()
) {

    val editableMode = remember { mutableStateOf(false) }
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
                    if (diary != null)
                        IconButton(onClick = {
                            editableMode.value = true
                        }) {
                            Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Edit")
                        }
                })
        }
    ) {
        if (diary == null || editableMode.value)
            EditableNote(
                modifier = Modifier.padding(it),
                item = diary,
                onChange = { title: String, content: String ->
                    notesViewModel.saveNewNote(title = title, content = content, item = diary)
                })
        else
            NotesContent(modifier = Modifier.padding(it), item = diary!!)
    }

}


@Composable
fun NotesContent(modifier: Modifier, item: DiaryDto) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrRtl),
        )
    }
}

@Composable
fun EditableNote(modifier: Modifier, item: DiaryDto?, onChange: (String, String) -> Unit) {

    var title by remember { mutableStateOf(item?.title ?: "") }
    var content by remember { mutableStateOf(item?.textContent ?: "") }
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    LaunchedEffect(title, content) {
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(500)
            onChange(title, content)
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { newValue: String ->
                title = newValue
            },
            placeholder = { Text(text = stringResource(id = R.string.label_note_screen_title)) },
            textStyle = MaterialTheme.typography.titleMedium.copy(textDirection = TextDirection.ContentOrRtl)
        )
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = content,
            onValueChange = { newValue: String ->
                content = newValue
            },
            placeholder = { Text(text = stringResource(id = R.string.label_note_screen_content)) },
            textStyle = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrRtl),
        )
    }
}

