package com.prev85.lifecalendar.ui.grid

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.ui.common.EntryDialog
import com.prev85.lifecalendar.util.Dates
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekGridScreen(
    onEvents: () -> Unit,
    onStats: () -> Unit,
    onSettings: () -> Unit,
    viewModel: WeekGridViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var editing by remember { mutableStateOf<Entry?>(null) }
    var adding by remember { mutableStateOf(false) }
    var addDate by remember { mutableStateOf(LocalDate.now()) }

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
                    state = state,
                    onEditChange = { editing = it },
                    onAddDate = { addDate = it },
                    onAddRequest = { adding = true }
                )
            }
        }
    }

    if (adding) {
        EntryDialog(
            entry = null,
            defaultDate = addDate,
            onDismiss = { adding = false },
            onSave = { date, text ->
                viewModel.addEntry(date, text)
                adding = false
            }
        )
    }
    editing?.let { entry ->
        EntryDialog(
            entry = entry,
            defaultDate = LocalDate.parse(entry.date),
            onDismiss = { editing = null },
            onSave = { date, text ->
                viewModel.updateEntry(entry.copy(date = Dates.iso(date), text = text))
                editing = null
            }
        )
    }
}

@Composable
private fun GridView(
    state: WeekGridViewModel.UiState,
    onEditChange: (Entry?) -> Unit,
    onAddDate: (LocalDate) -> Unit,
    onAddRequest: () -> Unit,
) {
    val density = LocalDensity.current.density
    val textMeasurer = rememberTextMeasurer()
    val gridColors = rememberGridColors()
    val overviewColors = rememberOverviewColors()
    val scope = rememberCoroutineScope()

    val cellPxBase = with(LocalDensity.current) { GridMetrics.CELL_DP.dp.toPx() }
    val yearGapPx = with(LocalDensity.current) { GridMetrics.YEAR_GAP_DP.dp.toPx() }
    val rowH = GridMetrics.rowHeight(cellPxBase, yearGapPx)
    val rows = state.weekKeys.size / GridMetrics.COLS

    var zoom by remember { mutableFloatStateOf(1f) }
    var pan by remember { mutableStateOf(Offset.Zero) }
    var inited by remember { mutableStateOf(false) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val vpW = with(LocalDensity.current) { maxWidth.toPx() }
        val vpH = with(LocalDensity.current) { maxHeight.toPx() }
        val totalH = rowH * rows
        val fitZoom = (vpH / totalH * 0.95f).coerceAtMost(1f)
        val minZoom = fitZoom
        val maxZoom = GridMetrics.MAX_ZOOM
        val cellPx = cellPxBase * zoom
        val lod3MinPx = GridMetrics.LOD3_MIN_DP * density
        val inOverview = cellPx >= lod3MinPx
        val lod2MinPx = GridMetrics.LOD2_MIN_DP * density

        fun clampPan() {
            val totalW = GridMetrics.COLS * cellPxBase * zoom
            pan = if (totalW <= vpW) {
                Offset((vpW - totalW) / 2f, pan.y)
            } else {
                Offset(pan.x.coerceIn(vpW - totalW, 0f), pan.y)
            }
            val th = totalH * zoom
            pan = if (th <= vpH) {
                Offset(pan.x, (vpH - th) / 2f)
            } else {
                Offset(pan.x, pan.y.coerceIn(vpH - th, 0f))
            }
        }

        fun worldOf(screen: Offset): Offset =
            Offset((screen.x + pan.x) / zoom, (screen.y + pan.y) / zoom)

        fun weekIndexAt(world: Offset): Int {
            val col = (world.x / cellPxBase).toInt()
            val row = (world.y / rowH).toInt()
            val idx = row * GridMetrics.COLS + col
            return if (row in 0 until rows && col in 0 until GridMetrics.COLS && idx in state.weekKeys.indices) idx else -1
        }

        fun focusWeekIndex(): Int {
            val c = worldOf(Offset(vpW / 2f, vpH / 2f))
            return weekIndexAt(c)
        }

        fun animateToWeek(idx: Int, targetZoom: Float) {
            val monday = state.weekKeys[idx]
            val worldCenter = Offset(
                ((idx % GridMetrics.COLS) + 0.5f) * cellPxBase,
                ((idx / GridMetrics.COLS) + 0.5f) * rowH
            )
            val tz = targetZoom.coerceIn(minZoom, maxZoom)
            scope.launch {
                animate(zoom, tz, animationSpec = tween(300)) { value, _ ->
                    zoom = value
                    pan = Offset(
                        worldCenter.x * value - vpW / 2f,
                        worldCenter.y * value - vpH / 2f
                    )
                    clampPan()
                }
            }
        }

        fun animateFit() {
            val todayIdx = state.weekKeys.indexOfFirst { Dates.iso(it) == state.todayKey }
                .coerceAtLeast(0)
            scope.launch {
                animate(zoom, minZoom, animationSpec = tween(350)) { value, _ ->
                    zoom = value
                    val centerY = (todayIdx / GridMetrics.COLS + 0.5f) * rowH * value
                    pan = Offset(0f, (centerY - vpH / 2f).coerceIn(0f, (totalH * value - vpH).coerceAtLeast(0f)))
                    clampPan()
                }
            }
        }

        fun animateToToday() {
            val idx = state.weekKeys.indexOfFirst { Dates.iso(it) == state.todayKey }
            if (idx >= 0) animateToWeek(idx, lod2MinPx / cellPxBase * 1.2f)
        }

        LaunchedEffect(state.weekKeys) {
            if (!inited && state.weekKeys.isNotEmpty()) {
                inited = true
                zoom = minZoom
                val todayIdx = state.weekKeys.indexOfFirst { Dates.iso(it) == state.todayKey }
                    .coerceAtLeast(0)
                val centerY = (todayIdx / GridMetrics.COLS + 0.5f) * rowH * minZoom
                pan = Offset(
                    (vpW - GridMetrics.COLS * cellPxBase * minZoom) / 2f,
                    (centerY - vpH / 2f).coerceIn(0f, (totalH * minZoom - vpH).coerceAtLeast(0f))
                )
                clampPan()
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            state.birthDate?.let { birth ->
                val today = LocalDate.now()
                val years = Period.between(birth, today).years
                val lastBday = birth.plusYears(years.toLong())
                val weeks = ChronoUnit.DAYS.between(lastBday, today) / 7
                Text(
                    text = "ДР ${Dates.ddmmyyyy(birth)} · Сегодня ${Dates.ddmmyyyy(today)} · " +
                        "$years г. $weeks нед. · Записей: ${state.entriesByWeek.size}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 2.dp)
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { centroid, gesturePan, gestureZoom, _ ->
                                val newZoom = (zoom * gestureZoom).coerceIn(minZoom, maxZoom)
                                val worldP = Offset(
                                    (centroid.x + pan.x) / zoom,
                                    (centroid.y + pan.y) / zoom
                                )
                                pan = Offset(
                                    worldP.x * newZoom - centroid.x - gesturePan.x,
                                    worldP.y * newZoom - centroid.y - gesturePan.y
                                )
                                zoom = newZoom
                                clampPan()
                            }
                        }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = { animateFit() },
                                onTap = { offset ->
                                    val world = worldOf(offset)
                                    val idx = weekIndexAt(world)
                                    if (idx < 0) return@detectTapGestures
                                    val monday = state.weekKeys[idx]
                                    if (cellPx >= lod3MinPx) {
                                        val focus = focusWeekIndex()
                                        if (idx == focus) {
                                            val wk = Dates.iso(monday)
                                            val blocks = computeWeekOverviewLayout(
                                                monday,
                                                state.entriesByWeek[wk].orEmpty(),
                                                state.eventsByWeek[wk].orEmpty(),
                                                cellPx,
                                                textMeasurer
                                            )
                                            val (day, entry) = hitTestWeekOverview(blocks, world.y - (idx / GridMetrics.COLS) * rowH)
                                            if (entry != null) {
                                                onEditChange(entry)
                                            } else if (day != LocalDate.MIN) {
                                                onAddDate(day)
                                                onAddRequest()
                                            }
                                        } else {
                                            animateToWeek(idx, zoom * 1.6f)
                                        }
                                    } else {
                                        animateToWeek(idx, lod2MinPx / cellPxBase * 1.2f)
                                    }
                                }
                            )
                        }
                ) {
                    val data = GridDrawData(
                        weekKeys = state.weekKeys,
                        entriesByWeek = state.entriesByWeek,
                        eventsByWeek = state.eventsByWeek,
                        todayKey = state.todayKey
                    )
                    drawGrid(
                        data = data,
                        zoom = zoom,
                        pan = pan,
                        cellW = cellPxBase,
                        cellH = cellPxBase,
                        rowH = rowH,
                        vpW = vpW,
                        vpH = vpH,
                        colors = gridColors,
                        overviewColors = overviewColors,
                        textMeasurer = textMeasurer,
                        density = density
                    )
                }

                if (inOverview) {
                    FloatingActionButton(
                        onClick = {
                            val focus = focusWeekIndex()
                            if (focus >= 0) {
                                onAddDate(state.weekKeys[focus])
                            }
                            onAddRequest()
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Добавить запись")
                    }
                }

                IconButton(
                    onClick = { animateToToday() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Filled.Today, contentDescription = "Сегодня")
                }
            }
        }
    }
}
