package com.lduboscq.minimedia.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lduboscq.minimedia.presentation.DetailViewModel
import com.lduboscq.minimedia.presentation.HomeViewModel

@Composable
fun MiniMediaNavGraph(
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            val state by homeViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) { homeViewModel.init() }

            HomeScreen(
                state = state,
                onClickArticle = { articleId: Long ->
                    navController.navigate("articles/$articleId")
                },
                //onClickVideo = { vm.openVideo(it) }
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
