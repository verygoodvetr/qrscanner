package com.example.qrscannerpro.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.qrscannerpro.ui.generator.GeneratorScreen
import com.example.qrscannerpro.ui.history.HistoryScreen
import com.example.qrscannerpro.ui.result.ResultScreen
import com.example.qrscannerpro.ui.scanner.ScannerScreen
import com.example.qrscannerpro.ui.settings.SettingsScreen
import com.example.qrscannerpro.ui.splash.SplashScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.Splash, modifier = modifier) {
        composable(NavRoutes.Splash) {
            SplashScreen(onContinue = { navController.navigate(NavRoutes.Scanner) { popUpTo(0) } })
        }
        composable(NavRoutes.Scanner) {
            ScannerScreen(
                onOpenResult = { navController.navigate(NavRoutes.result(it)) },
                onOpenHistory = { navController.navigate(NavRoutes.History) },
                onOpenGenerator = { navController.navigate(NavRoutes.Generator) },
                onOpenSettings = { navController.navigate(NavRoutes.Settings) },
            )
        }
        composable(
            NavRoutes.Result,
            arguments = listOf(navArgument("content") { type = NavType.StringType })
        ) { entry ->
            ResultScreen(
                content = entry.arguments?.getString("content").orEmpty(),
                onBack = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.History) { HistoryScreen(onBack = { navController.popBackStack() }) }
        composable(NavRoutes.Generator) { GeneratorScreen(onBack = { navController.popBackStack() }) }
        composable(NavRoutes.Settings) { SettingsScreen(onBack = { navController.popBackStack() }) }
    }
}
