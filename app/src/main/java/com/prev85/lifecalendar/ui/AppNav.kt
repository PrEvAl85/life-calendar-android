package com.prev85.lifecalendar.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prev85.lifecalendar.ui.events.EventsScreen
import com.prev85.lifecalendar.ui.grid.WeekGridScreen
import com.prev85.lifecalendar.ui.settings.SettingsScreen
import com.prev85.lifecalendar.ui.stats.StatsScreen
import com.prev85.lifecalendar.ui.week.WeekScreen

object Routes {
    const val GRID = "grid"
    const val WEEK = "week/{monday}"
    const val EVENTS = "events"
    const val STATS = "stats"
    const val SETTINGS = "settings"

    fun week(monday: String) = "week/$monday"
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.GRID) {
        composable(Routes.GRID) {
            WeekGridScreen(
                onWeekClick = { nav.navigate(Routes.week(it)) },
                onEvents = { nav.navigate(Routes.EVENTS) },
                onStats = { nav.navigate(Routes.STATS) },
                onSettings = { nav.navigate(Routes.SETTINGS) }
            )
        }
        composable(
            Routes.WEEK,
            arguments = listOf(navArgument("monday") { type = NavType.StringType })
        ) { entry ->
            val monday = entry.arguments?.getString("monday").orEmpty()
            WeekScreen(monday = monday, onBack = { nav.popBackStack() })
        }
        composable(Routes.EVENTS) {
            EventsScreen(onBack = { nav.popBackStack() })
        }
        composable(Routes.STATS) {
            StatsScreen(onBack = { nav.popBackStack() })
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(onBack = { nav.popBackStack() })
        }
    }
}
