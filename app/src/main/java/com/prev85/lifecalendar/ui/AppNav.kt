package com.prev85.lifecalendar.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prev85.lifecalendar.LifeCalendarApp
import com.prev85.lifecalendar.ui.entries.EntriesScreen
import com.prev85.lifecalendar.ui.events.EventsScreen
import com.prev85.lifecalendar.ui.grid.WeekGridScreen
import com.prev85.lifecalendar.ui.onboarding.OnboardingScreen
import com.prev85.lifecalendar.ui.profile.ProfileScreen
import com.prev85.lifecalendar.ui.week.WeekScreen
import com.prev85.lifecalendar.util.Dates
import kotlinx.coroutines.flow.first
import java.time.LocalDate

object Routes {
    const val TODAY = "today"
    const val MAP = "map"
    const val ENTRIES = "entries"
    const val EVENTS = "events"
    const val PROFILE = "profile"
    const val WEEK = "week/{monday}"

    fun week(monday: String) = "week/$monday"
}

private val TAB_ROUTES = setOf(
    Routes.TODAY, Routes.MAP, Routes.ENTRIES, Routes.EVENTS, Routes.PROFILE
)

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val context = LocalContext.current
    val settings = (context.applicationContext as LifeCalendarApp).settings

    var ready by remember { mutableStateOf(false) }
    var hasBirth by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        hasBirth = settings.birthDate.first() != null
        ready = true
    }

    if (!ready) {
        Box(modifier = Modifier.fillMaxSize())
        return
    }

    if (!hasBirth) {
        OnboardingScreen(onDone = { hasBirth = true })
        return
    }

    val backStack by nav.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val showBottomBar = currentRoute in TAB_ROUTES

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    TabItem(
                        label = "Сегодня",
                        icon = Icons.Filled.Today,
                        selected = currentRoute == Routes.TODAY,
                        onSelect = {
                            nav.navigate(Routes.TODAY) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    TabItem(
                        label = "Карта",
                        icon = Icons.Filled.Map,
                        selected = currentRoute == Routes.MAP,
                        onSelect = {
                            nav.navigate(Routes.MAP) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    TabItem(
                        label = "Дневник",
                        icon = Icons.AutoMirrored.Filled.List,
                        selected = currentRoute == Routes.ENTRIES,
                        onSelect = {
                            nav.navigate(Routes.ENTRIES) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    TabItem(
                        label = "События",
                        icon = Icons.Filled.Event,
                        selected = currentRoute == Routes.EVENTS,
                        onSelect = {
                            nav.navigate(Routes.EVENTS) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    TabItem(
                        label = "Профиль",
                        icon = Icons.Filled.Person,
                        selected = currentRoute == Routes.PROFILE,
                        onSelect = {
                            nav.navigate(Routes.PROFILE) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Routes.TODAY,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.TODAY) {
                WeekScreen(
                    monday = Dates.iso(Dates.mondayOf(LocalDate.now())),
                    onBack = {},
                    showBack = false
                )
            }
            composable(Routes.MAP) {
                WeekGridScreen(onWeekClick = { monday ->
                    nav.navigate(Routes.week(monday))
                })
            }
            composable(Routes.ENTRIES) {
                EntriesScreen()
            }
            composable(Routes.EVENTS) {
                EventsScreen()
            }
            composable(Routes.PROFILE) {
                ProfileScreen()
            }
            composable(
                Routes.WEEK,
                arguments = listOf(navArgument("monday") { type = NavType.StringType })
            ) { entry ->
                val monday = entry.arguments?.getString("monday").orEmpty()
                WeekScreen(monday = monday, onBack = { nav.popBackStack() })
            }
        }
    }
}

@Composable
private fun RowScope.TabItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onSelect,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) }
    )
}
