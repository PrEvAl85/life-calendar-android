package com.prev85.lifecalendar.ui.grid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

data class DayBlock(
    val date: LocalDate,
    val top: Float,
    val height: Float,
    val entries: List<Entry>,
    val events: List<Event>,
    val entryTops: List<Float>,
    val eventTops: List<Float>,
)

private val DAY_NAMES = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

private fun TextMeasurer.measureWrapped(text: String, style: TextStyle, maxWidth: Float): TextLayoutResult =
    measure(
        AnnotatedString(text),
        style = style,
        constraints = Constraints(maxWidth = maxWidth.toInt())
    )

/**
 * Геометрия развёрнутой недели: 7 дней сверху вниз.
 * `block.top` — от начала ячейки недели; `entryTops`/`eventTops` — от начала дня.
 * Детерминирована: используется и для отрисовки, и для hit-test.
 */
fun computeWeekOverviewLayout(
    monday: LocalDate,
    entries: List<Entry>,
    events: List<Event>,
    width: Float,
    textMeasurer: TextMeasurer,
): List<DayBlock> {
    val pad = 5f
    val headerH = 24f
    val labelH = 18f
    val entryStyle = TextStyle(fontSize = 14.sp)
    val eventStyle = TextStyle(fontSize = 12.sp)
    val blocks = mutableListOf<DayBlock>()
    var y = headerH
    for (i in 0 until 7) {
        val date = monday.plusDays(i.toLong())
        val dayEntries = entries.filter { LocalDate.parse(it.date) == date }
        val dayEvents = events.filter { LocalDate.parse(it.date) == date }
        var cursor = pad + labelH
        val entryTops = mutableListOf<Float>()
        val eventTops = mutableListOf<Float>()
        for (ev in dayEvents) {
            eventTops.add(cursor)
            cursor += textMeasurer.measureWrapped(ev.title, eventStyle, width - 2 * pad).size.height.toFloat() + 3f
        }
        for (e in dayEntries) {
            entryTops.add(cursor)
            val text = e.text.replace('\n', ' ').trim()
            cursor += textMeasurer.measureWrapped(text, entryStyle, width - 2 * pad).size.height.toFloat() + 4f
        }
        blocks.add(DayBlock(date, y, cursor + pad, dayEntries, dayEvents, entryTops, eventTops))
        y += cursor + pad
    }
    return blocks
}

data class OverviewColors(
    val header: Color,
    val date: Color,
    val todayDate: Color,
    val text: Color,
    val divider: Color,
)

fun drawWeekOverview(
    scope: DrawScope,
    monday: LocalDate,
    blocks: List<DayBlock>,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    colors: OverviewColors,
    textMeasurer: TextMeasurer,
) {
    scope.drawRect(
        color = colors.divider.copy(alpha = 0.20f),
        topLeft = Offset(x, y),
        size = Size(width, height)
    )

    val header = textMeasurer.measureWrapped(
        Dates.formatWeekRange(monday),
        TextStyle(fontSize = 14.sp, color = colors.header),
        width
    )
    scope.drawText(textLayoutResult = header, topLeft = Offset(x + 6f, y + 3f))

    for (block in blocks) {
        val by = y + block.top
        if (by > y + height) break
        if (by + block.height < y) continue
        scope.drawRect(
            color = colors.divider.copy(alpha = 0.35f),
            topLeft = Offset(x, by),
            size = Size(width, 1f)
        )
        val isTodayDay = block.date == LocalDate.now()
        val dateLabel = textMeasurer.measureWrapped(
            "${DAY_NAMES[block.date.dayOfWeek.value - 1]} ${String.format("%02d.%02d", block.date.dayOfMonth, block.date.monthValue)}",
            TextStyle(fontSize = 12.sp, color = if (isTodayDay) colors.todayDate else colors.date),
            width
        )
        scope.drawText(textLayoutResult = dateLabel, topLeft = Offset(x + 6f, by + 4f))
        val labelH = dateLabel.size.height.toFloat()

        for ((idx, ev) in block.events.withIndex()) {
            val evLabel = textMeasurer.measureWrapped("• ${ev.title}", TextStyle(fontSize = 12.sp, color = colors.text), width)
            val ey = by + 4f + labelH + block.eventTops[idx] - block.eventTops.first()
            scope.drawRect(
                color = Color(ev.color),
                topLeft = Offset(x + 6f, ey + 2f),
                size = Size(8f, 9f)
            )
            scope.drawText(textLayoutResult = evLabel, topLeft = Offset(x + 18f, ey))
        }
        for ((idx, e) in block.entries.withIndex()) {
            val text = e.text.replace('\n', ' ').trim()
            val layout = textMeasurer.measureWrapped(text, TextStyle(fontSize = 14.sp, color = colors.text), width)
            val ey = by + 4f + labelH + block.entryTops[idx] - (block.entryTops.firstOrNull() ?: block.entryTops[idx])
            scope.drawText(textLayoutResult = layout, topLeft = Offset(x + 6f, ey))
        }
    }
}

/** Возвращает (дата дня, запись) по точке внутри ячейки недели; запись null — тап по пустому месту дня. */
fun hitTestWeekOverview(
    blocks: List<DayBlock>,
    y: Float,
): Pair<LocalDate, Entry?> {
    for (block in blocks) {
        if (y < block.top || y >= block.top + block.height) continue
        val yInDay = y - block.top
        for ((idx, entry) in block.entries.withIndex()) {
            val top = block.entryTops[idx] - 1f
            if (yInDay >= top && yInDay < top + 22f) return block.date to entry
        }
        return block.date to null
    }
    return LocalDate.MIN to null
}
