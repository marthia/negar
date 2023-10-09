package me.marthia.negar.framework.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.marthia.negar.framework.presentation.note.NoteListScreen
import me.marthia.negar.framework.presentation.note.detail.NoteScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "notelist") {
        composable("notelist") { NoteListScreen(navController = navController) }
        composable("note/{id}")
        { backStackEntry ->
            backStackEntry.arguments?.getLong("noteId")?.let { NoteScreen(it) }
        }
    }

}