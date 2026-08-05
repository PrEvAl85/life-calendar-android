package com.prev85.lifecalendar.ui.grid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.util.Dates
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekGridScreen(
    onWeekClick: (String) -> Unit,
    onEvents: () -> Unit,
    onStats: () -> Unit,
    onSettings: () -> Unit,
    viewModel: WeekGridViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        state.birthDate?.let { "Календарь жизни · ${it.year}" } ?: "Календарь жизни"
                    )
                },
                actions = {
                    IconButton(onClick = onEvents) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "События")
                    }
                    IconButton(onClick = onStats) {
                        Icon(Icons.Filled.BarChart, contentDescription = "Статистика")
                    }
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Filled.Settings, contentDescription = "Настройки")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.birthDate == null -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Задайте дату рождения в настройках")
                }
                state.weekKeys.isEmpty() -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                else -> GridView(
                    weekKeys = state.weekKeys,
                    entriesByWeek = state.entriesByWeek,
                    eventsByWeek = state.eventsByWeek,
                    todayKey = state.todayKey,
                    onWeekClick = onWeekClick
                )
            }
        }
    }
}

@Composable
private fun GridView(
    weekKeys: List<LocalDate>,
    entriesByWeek: Map<String, List<Entry>>,
    eventsByWeek: Map<String, List<Event>>,
    todayKey: String,
    onWeekClick: (String) -> Unit,
) {
    val density = LocalDensity.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var scale by remember { mutableFloatStateOf(1f) }
    val drawer = rememberGridDrawer()

    val cellH = with(density) { GridMetrics.CELL_H_DP.dp.toPx() }
    val yearGap = with(density) { GridMetrics.YEAR_GAP_DP.dp.toPx() }
    val rowH = GridMetrics.rowHeight(cellH, yearGap)
    val rows = weekKeys.size / GridMetrics.COLS
    val totalH = rowH * rows
    val totalHdp = with(density) { totalH.toDp() }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val vpH = with(density) { maxHeight.toPx() }
        val vpW = with(density) { maxWidth.toPx() }
        val fitScale = vpH / totalH * 0.95f

        LaunchedEffect(weekKeys) {
            val todayIdx = weekKeys.indexOfFirst { Dates.iso(it) == todayKey }
            if (todayIdx >= 0) {
                scrollState.scrollTo((todayIdx / GridMetrics.COLS * rowH).toInt())
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset ->
                            val contentY =
                                (offset.y - vpH / 2f) / scale + vpH / 2f + scrollState.value
                            val row = floor(contentY / rowH).toInt()
                            val col = (offset.x / (vpW / GridMetrics.COLS)).toInt()
                            val idx = row * GridMetrics.COLS + col
                            if (row >= 0 && col in 0 until GridMetrics.COLS && idx in weekKeys.indices) {
                                onWeekClick(Dates.iso(weekKeys[idx]))
                            }
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        scale = (scale * zoom).coerceIn(fitScale, 4f)
                    }
                }
                .verticalScroll(scrollState)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(totalHdp)
            ) {
                val visibleTop = scrollState.value.toFloat()
                val visibleBottom = visibleTop + size.height / scale
                drawer(
                    this,
                    GridDrawData(weekKeys, entriesByWeek, eventsByWeek, todayKey),
                    vpW / GridMetrics.COLS,
                    cellH,
                    scale,
                    visibleTop,
                    visibleBottom
                )
            }
        }

        IconButton(
            onClick = {
                val todayIdx = weekKeys.indexOfFirst { Dates.iso(it) == todayKey }
                if (todayIdx >= 0) {
                    scope.launch {
                        scrollState.animateScrollTo((todayIdx / GridMetrics.COLS * rowH).toInt())
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Today, contentDescription = "Сегодня")
        }
    }
}
