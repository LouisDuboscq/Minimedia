package com.lduboscq.minimedia.android

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lduboscq.minimedia.presentation.DetailViewModel
import com.lduboscq.minimedia.presentation.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun MiniMediaNavGraph(
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            val state by homeViewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) { homeViewModel.init() }

            HomeScreen(
                state = state,
                onClickArticle = { articleId: Long ->
                    navController.navigate("articles/$articleId")
                },
                onClickVideo = { scope.launch { homeViewModel.onClickVideo(it) } },
                effects = homeViewModel.effect,
                onDispose = { scope.launch { homeViewModel.onDispose(it) } },
                navigateBack = { (context as? Activity)?.finish() }
            )
        }

        composable(
            "articles/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val articleId =
                backStackEntry.arguments?.getLong("id")
                    ?: throw IllegalAccessException()

            val state by detailViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                detailViewModel.init(articleId)
            }

            StoryDetailScreen(
                state = state,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
