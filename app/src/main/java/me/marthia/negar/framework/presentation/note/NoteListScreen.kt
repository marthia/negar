@file:OptIn(ExperimentalMaterial3Api::class)

package me.marthia.negar.framework.presentation.note

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import me.marthia.negar.R
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.framework.presentation.ui.ErrorMessage
import me.marthia.negar.framework.presentation.ui.LoadingNextPageItem
import me.marthia.negar.framework.presentation.ui.PageLoader

@Composable
fun NoteListScreen(
    notesViewModel: NotesViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController, notesViewModel = notesViewModel)
        }
    ) {
        NoteListScreenContent(
            modifier = Modifier.padding(it),
            notesViewModel = notesViewModel,
            navController = navController
        )
    }
}

@Composable
fun NoteListScreenContent(
    modifier: Modifier = Modifier,
    notesViewModel: NotesViewModel,
    navController: NavHostController,

    ) {
    val notes = notesViewModel.notesList.collectAsLazyPagingItems()


    LazyColumn(modifier = Modifier.then(modifier)) {
        items(notes.itemCount) { index ->
            notes[index]?.let {
                NoteItem(item = it, onClick = {
                    navController.navigate("note?diaryId=${it.diaryId}")
                })
            }
        }
        notes.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = notes.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = notes.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }

}

@Composable
fun NoteItem(item: DiaryDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        elevation = CardDefaults.cardElevation(),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        onClick = { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
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
}

@Composable
fun BottomNavigation(navController: NavHostController, notesViewModel: NotesViewModel) {

    val progress = remember { mutableStateOf(false) }
    ProgressDialog(visible = progress.value, viewModel = notesViewModel) {
        progress.value = false
    }
    val pickPictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uri ->
        progress.value = true
        notesViewModel.saveFromUri(uri)

    }

    BottomAppBar(
        actions = {
            IconButton(onClick = { pickPictureLauncher.launch(arrayOf("*/*")) }) {
                Icon(Icons.Rounded.Build, contentDescription = "Localized description")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Share,
                    contentDescription = "Localized description",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate("note?diaryId=-1")
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )
}

@Composable
fun ProgressDialog(visible: Boolean = false, viewModel: NotesViewModel, onDismiss: () -> Unit) {
    val progress by viewModel.importingProgress.collectAsState()
    if (visible) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ), onDismissRequest = {
                onDismiss()
            }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentColor = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.importing_from_json),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    LinearProgressIndicator(progress = progress)
                    if (progress == 1.0f)
                        onDismiss()
                }
            }
        }
    }
}