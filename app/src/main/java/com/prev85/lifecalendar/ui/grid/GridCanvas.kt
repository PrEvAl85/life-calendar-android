package com.prev85.lifecalendar.ui.grid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate
import java.time.YearMonth

object GridMetrics {
    const val COLS = 52
    const val CELL_H_DP = 9f
    const val YEAR_GAP_DP = 16f

    fun rowHeight(cellH: Float, yearGap: Float): Float = COLS * cellH + yearGap
}

data class GridDrawData(
    val weekKeys: List<LocalDate>,
    val entriesByWeek: Map<String, List<Entry>>,
    val eventsByWeek: Map<String, List<Event>>,
    val todayKey: String,
)

data class GridColors(
    val bgEven: Color,
    val bgOdd: Color,
    val lived: Color,
    val future: Color,
    val birthday: Color,
    val note: Color,
    val today: Color,
    val label: Color,
)

fun lightGridColors() = GridColors(
    bgEven = Color(0xFFF6F6F6),
    bgOdd = Color(0xFFFFFFFF),
    lived = Color(0xFF9E9E9E),
    future = Color(0xFFE6E6E6),
    birthday = Color(0xFF4CAF50),
    note = Color(0xFFFFC107),
    today = Color(0xFFEF5350),
    label = Color(0xAA666666),
)

fun darkGridColors() = GridColors(
    bgEven = Color(0xFF1E1E1E),
    bgOdd = Color(0xFF1A1A1A),
    lived = Color(0xFF6E6E6E),
    future = Color(0xFF333333),
    birthday = Color(0xFF4CAF50),
    note = Color(0xFFFFC107),
    today = Color(0xFFEF5350),
    label = Color(0xAA9E9E9E),
)

@Composable
fun rememberGridDrawer(): (DrawScope, GridDrawData, Float, Float, Float, Float, Float) -> Unit {
    val textMeasurer = rememberTextMeasurer()
    val colors = if (isSystemInDarkTheme()) darkGridColors() else lightGridColors()
    return { scope, data, cellW, cellH, scale, visibleTop, visibleBottom ->
        scope.drawGrid(data, cellW, cellH, scale, visibleTop, visibleBottom, textMeasurer, colors)
    }
}

private fun DrawScope.drawGrid(
    data: GridDrawData,
    cellW: Float,
    cellH: Float,
    scale: Float,
    visibleTop: Float,
    visibleBottom: Float,
    textMeasurer: TextMeasurer,
    colors: GridColors,
) {
    val rowH = GridMetrics.rowHeight(cellH, GridMetrics.YEAR_GAP_DP)
    val rows = data.weekKeys.size / GridMetrics.COLS
    if (rows == 0) return
    val bornYear = data.weekKeys.first().year
    val birthMonth = data.weekKeys.first().monthValue
    val birthDay = data.weekKeys.first().dayOfMonth

    val firstRow = (visibleTop / rowH).toInt().coerceIn(0, rows - 1)
    val lastRow = (visibleBottom / rowH).toInt().coerceIn(0, rows - 1)

    for (r in firstRow..lastRow) {
        val y0 = r * rowH
        drawRect(
            color = if (r % 2 == 0) colors.bgEven else colors.bgOdd,
            topLeft = Offset(0f, y0),
            size = Size(size.width, rowH)
        )
        for (c in 0 until GridMetrics.COLS) {
            val idx = r * GridMetrics.COLS + c
            if (idx >= data.weekKeys.size) break
            val mon = data.weekKeys[idx]
            val x0 = c * cellW
            val cellSize = Size(cellW - 0.5f, cellH - 0.5f)

            val lived = mon.plusDays(6).isBefore(LocalDate.now())
            val color = when {
                isBirthdayWeek(mon, birthMonth, birthDay) -> colors.birthday.copy(alpha = 0.35f)
                lived -> colors.lived.copy(alpha = 0.55f)
                else -> colors.future
            }
            drawRect(color = color, topLeft = Offset(x0, y0), size = cellSize)

            val wk = Dates.iso(mon)
            if (wk == data.todayKey) {
                drawRect(
                    color = colors.today,
                    topLeft = Offset(x0, y0),
                    size = cellSize,
                    style = Stroke(width = 1.5f)
                )
            }

            data.eventsByWeek[wk]?.let { evs ->
                val ev = evs.first()
                drawRect(
                    color = Color(ev.color),
                    topLeft = Offset(x0 + cellW * 0.18f, y0 + cellH * 0.12f),
                    size = Size(cellW * 0.64f, cellH * 0.32f)
                )
            }
            if (data.entriesByWeek.containsKey(wk)) {
                drawRect(
                    color = colors.note,
                    topLeft = Offset(x0 + cellW * 0.55f, y0 + cellH * 0.55f),
                    size = Size(cellW * 0.35f, cellH * 0.35f)
                )
            }
        }

        if (scale < 0.7f) {
            drawText(
                textMeasurer = textMeasurer,
                text = (bornYear + r).toString(),
                topLeft = Offset(size.width / 2 - 20f, y0 + 2f),
                style = androidx.compose.ui.text.TextStyle(
                    color = colors.label,
                    fontSize = 11.sp
                )
            )
        }
    }
}

private fun isBirthdayWeek(mon: LocalDate, birthMonth: Int, birthDay: Int): Boolean {
    val day = minOf(birthDay, YearMonth.of(mon.year, birthMonth).lengthOfMonth())
    val anniv = runCatching { LocalDate.of(mon.year, birthMonth, day) }.getOrNull() ?: return false
    return !anniv.isBefore(mon) && anniv.isBefore(mon.plusDays(7))
}
