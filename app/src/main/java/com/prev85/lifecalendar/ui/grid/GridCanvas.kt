package com.prev85.lifecalendar.ui.grid

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

object GridMetrics {
    const val COLS = 52
    const val CELL_DP = 7f
    const val YEAR_GAP_DP = 3f

    const val LOD2_MIN_DP = 22f
    const val LOD3_MIN_DP = 70f
    const val MAX_ZOOM = 160f

    fun rowHeight(cell: Float, yearGap: Float): Float = cell + yearGap
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
    val empty: Color,
    val birthday: Color,
    val note: Color,
    val todayStroke: Color,
    val label: Color,
    val previewDate: Color,
    val previewText: Color,
)

fun lightGridColors() = GridColors(
    bgEven = Color(0xFFF6F6F6),
    bgOdd = Color(0xFFFFFFFF),
    lived = Color(0xFFDD2222),
    empty = Color(0xFFBBBBBB),
    birthday = Color(0xFF3CB371),
    note = Color(0xFFFFD500),
    todayStroke = Color(0xFFE53935),
    label = Color(0xAA666666),
    previewDate = Color(0xFF555555),
    previewText = Color(0xFF333333),
)

fun darkGridColors() = GridColors(
    bgEven = Color(0xFF202020),
    bgOdd = Color(0xFF1A1A1A),
    lived = Color(0xFFFF6B6B),
    empty = Color(0xFF4A4A4A),
    birthday = Color(0xFF5FD48A),
    note = Color(0xFFFFD500),
    todayStroke = Color(0xFFFF8A80),
    label = Color(0xAA9E9E9E),
    previewDate = Color(0xFFBDBDBD),
    previewText = Color(0xFFE0E0E0),
)

fun lightOverviewColors() = OverviewColors(
    header = Color(0xFF333333),
    date = Color(0xFF666666),
    todayDate = Color(0xFFE53935),
    text = Color(0xFF222222),
    divider = Color(0xFF999999),
)

fun darkOverviewColors() = OverviewColors(
    header = Color(0xFFDDDDDD),
    date = Color(0xFFAAAAAA),
    todayDate = Color(0xFFFF8A80),
    text = Color(0xFFF0F0F0),
    divider = Color(0xFF777777),
)

/** Классический контур сердца в единичном квадрате [0,1]x[0,1], центрированный по y. */
private fun heartPath(size: Float): Path = Path().apply {
    val s = size
    moveTo(0.5f * s, 0.26f * s)
    cubicTo(0.5f * s, 0.18f * s, 0.38f * s, 0.08f * s, 0.24f * s, 0.14f * s)
    cubicTo(0.08f * s, 0.2f * s, 0.06f * s, 0.38f * s, 0.18f * s, 0.52f * s)
    cubicTo(0.3f * s, 0.66f * s, 0.46f * s, 0.8f * s, 0.5f * s, 0.86f * s)
    cubicTo(0.54f * s, 0.8f * s, 0.7f * s, 0.66f * s, 0.82f * s, 0.52f * s)
    cubicTo(0.94f * s, 0.38f * s, 0.92f * s, 0.2f * s, 0.76f * s, 0.14f * s)
    cubicTo(0.62f * s, 0.08f * s, 0.5f * s, 0.18f * s, 0.5f * s, 0.26f * s)
    close()
}

private fun DrawScope.drawHeart(cx: Float, cy: Float, size: Float, color: Color, stroke: Float? = null) {
    val path = heartPath(size)
    path.translate(Offset(cx - size / 2f, cy - size / 2f))
    if (stroke != null) {
        drawPath(path, color, style = Stroke(width = stroke))
    } else {
        drawPath(path, color)
    }
}

private fun TextMeasurer.measureWrapped(text: String, style: TextStyle, maxWidth: Float): TextLayoutResult =
    measure(
        AnnotatedString(text),
        style = style,
        constraints = Constraints(maxWidth = maxWidth.toInt())
    )

private fun isBirthdayWeek(mon: LocalDate, birthMonth: Int, birthDay: Int): Boolean {
    val day = minOf(birthDay, java.time.YearMonth.of(mon.year, birthMonth).lengthOfMonth())
    val anniv = runCatching { LocalDate.of(mon.year, birthMonth, day) }.getOrNull() ?: return false
    return !anniv.isBefore(mon) && anniv.isBefore(mon.plusDays(7))
}

@Composable
fun rememberGridColors(): GridColors =
    if (isSystemInDarkTheme()) darkGridColors() else lightGridColors()

@Composable
fun rememberOverviewColors(): OverviewColors =
    if (isSystemInDarkTheme()) darkOverviewColors() else lightOverviewColors()

/**
 * Рисует сетку «годы × 52 недели» с LOD:
 *  - LOD1 (ячейка < 22dp): сердечки, точка записи, обводка «сегодня», подписи годов;
 *  - LOD2 (22..70dp): сердце + дата недели + превью записей;
 *  - LOD3 (>= 70dp): развёрнутая неделя (7 дней) для фокусной ячейки, остальные — сердечки.
 */
fun DrawScope.drawGrid(
    data: GridDrawData,
    zoom: Float,
    pan: Offset,
    cellW: Float,
    cellH: Float,
    rowH: Float,
    vpW: Float,
    vpH: Float,
    colors: GridColors,
    overviewColors: OverviewColors,
    textMeasurer: TextMeasurer,
    density: Float,
) {
    val rows = data.weekKeys.size / GridMetrics.COLS
    if (rows == 0) return
    val bornYear = data.weekKeys.first().year
    val birthMonth = data.weekKeys.first().monthValue
    val birthDay = data.weekKeys.first().dayOfMonth
    val totalH = rowH * rows

    fun world(sx: Float, sy: Float): Offset =
        Offset((sx + pan.x) / zoom, (sy + pan.y) / zoom)

    val w0 = world(0f, 0f)
    val w1 = world(vpW, vpH)
    val firstRow = (w0.y / rowH).toInt().coerceIn(0, rows - 1)
    val lastRow = (w1.y / rowH).toInt().coerceIn(firstRow, rows - 1)
    val firstCol = (w0.x / cellW).toInt().coerceIn(0, GridMetrics.COLS - 1)
    val lastCol = (w1.x / cellW).toInt().coerceIn(firstCol, GridMetrics.COLS - 1)

    val cellPx = cellW * zoom
    val lod3 = cellPx >= GridMetrics.LOD3_MIN_DP * density
    val lod2 = cellPx >= GridMetrics.LOD2_MIN_DP * density

    val focusIdx = if (lod3) {
        val c = world(vpW / 2f, vpH / 2f)
        val col = (c.x / cellW).toInt()
        val row = (c.y / rowH).toInt()
        val i = row * GridMetrics.COLS + col
        if (row in 0 until rows && col in 0 until GridMetrics.COLS && i in data.weekKeys.indices) i else -1
    } else -1

    for (r in firstRow..lastRow) {
        val y0 = (r * rowH) * zoom - pan.y
        drawRect(
            color = if (r % 2 == 0) colors.bgEven else colors.bgOdd,
            topLeft = Offset(0f, y0),
            size = Size(vpW, rowH * zoom)
        )
        for (c in firstCol..lastCol) {
            val idx = r * GridMetrics.COLS + c
            if (idx >= data.weekKeys.size) break
            val mon = data.weekKeys[idx]
            val x0 = (c * cellW) * zoom - pan.x
            val cy = y0 + cellH * zoom / 2f
            val wk = Dates.iso(mon)
            val events = data.eventsByWeek[wk]
            val hasNotes = data.entriesByWeek.containsKey(wk)
            val isToday = wk == data.todayKey

            if (lod3 && idx == focusIdx) {
                val blocks = computeWeekOverviewLayout(
                    mon,
                    data.entriesByWeek[wk] ?: emptyList(),
                    events ?: emptyList(),
                    cellW * zoom,
                    textMeasurer
                )
                drawWeekOverview(
                    this, mon, blocks,
                    x0, y0, cellW * zoom, cellH * zoom,
                    overviewColors, textMeasurer
                )
                continue
            }

            val lived = mon.plusDays(6).isBefore(LocalDate.now())
            val heartColor = when {
                isBirthdayWeek(mon, birthMonth, birthDay) -> colors.birthday
                events != null && events.isNotEmpty() -> Color(events.first().color)
                lived -> colors.lived
                else -> colors.empty
            }

            if (lod2) {
                val heartSize = cellW * zoom * 0.5f
                drawHeart(x0 + heartSize / 2f, cy, heartSize, heartColor)
                if (isToday) {
                    drawHeart(x0 + heartSize / 2f, cy, heartSize, colors.todayStroke, stroke = 1.5f * density)
                }
                if (hasNotes) {
                    drawCircle(
                        color = colors.note,
                        radius = heartSize * 0.14f,
                        center = Offset(x0 + heartSize * 0.8f, cy - heartSize * 0.3f)
                    )
                }
                val textX = x0 + heartSize + 4f * density
                val textW = cellW * zoom - heartSize - 6f * density
                if (textW > 8f * density) {
                    val date = textMeasurer.measureWrapped(
                        "${String.format("%02d.%02d", mon.dayOfMonth, mon.monthValue)}",
                        TextStyle(fontSize = 10.sp, color = colors.previewDate),
                        textW
                    )
                    drawText(textLayoutResult = date, topLeft = Offset(textX, cy - 10f * density))
                    val entry = data.entriesByWeek[wk]?.firstOrNull()
                    if (entry != null) {
                        val preview = entry.text.replace('\n', ' ').trim().take(70)
                        val pv = textMeasurer.measureWrapped(
                            preview,
                            TextStyle(fontSize = 9.sp, color = colors.previewText),
                            textW
                        )
                        val height = pv.size.height.toFloat()
                        val visibleLines = ((cellW * zoom * 0.5f) / (height / pv.lineCount)).toInt().coerceAtLeast(1)
                        if (visibleLines < pv.lineCount) {
                            val clipped = preview.take((preview.length * visibleLines / pv.lineCount).coerceAtLeast(8))
                            drawText(
                                textLayoutResult = textMeasurer.measureWrapped(
                                    clipped,
                                    TextStyle(fontSize = 9.sp, color = colors.previewText),
                                    textW
                                ),
                                topLeft = Offset(textX, cy + 2f * density)
                            )
                        } else {
                            drawText(textLayoutResult = pv, topLeft = Offset(textX, cy + 2f * density))
                        }
                    }
                }
            } else {
                val heartSize = cellW * zoom * 0.9f
                drawHeart(x0 + cellW * zoom / 2f, cy, heartSize, heartColor)
                if (isToday) {
                    drawHeart(x0 + cellW * zoom / 2f, cy, heartSize, colors.todayStroke, stroke = 1.2f * density)
                }
                if (hasNotes) {
                    drawCircle(
                        color = colors.note,
                        radius = heartSize * 0.16f,
                        center = Offset(x0 + cellW * zoom * 0.78f, y0 + cellH * zoom * 0.22f)
                    )
                }
            }
        }

        if (!lod3 && cellPx < 10f * density) {
            drawText(
                textMeasurer = textMeasurer,
                text = (bornYear + r).toString(),
                topLeft = Offset(vpW / 2f - 22f, y0 + 1f),
                style = TextStyle(color = colors.label, fontSize = 9.sp)
            )
        } else if (!lod3 && cellPx < 22f * density) {
            drawText(
                textMeasurer = textMeasurer,
                text = (bornYear + r).toString(),
                topLeft = Offset(vpW - 44f, y0 + 1f),
                style = TextStyle(color = colors.label, fontSize = 9.sp)
            )
        }
    }
}
