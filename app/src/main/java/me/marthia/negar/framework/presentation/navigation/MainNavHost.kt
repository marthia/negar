package me.marthia.negar.framework.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.marthia.negar.framework.presentation.note.NoteListScreen
import me.marthia.negar.framework.presentation.note.detail.NoteScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "notelist") {
        composable(route = "notelist") { NoteListScreen(navController = navController) }
        composable(route = "note?diaryId={id}", arguments = listOf(
            navArgument("id") { type = NavType.LongType }
        ),
            )
        {
            NoteScreen(navController = navController)
        }
    }
}