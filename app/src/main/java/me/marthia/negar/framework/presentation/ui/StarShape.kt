package me.marthia.negar.framework.presentation.ui

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class StarShape(
    private val spikes: Int,
    private val outerRadius: Float,
    private val innerRadius: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val cx = size.width/2
            val cy = size.height/2

            var rot = PI / 2 * 3
            var x = cx
            var y = cy
            val step = PI / spikes

            moveTo(cx, cy - outerRadius)
            for (i in 0 until spikes) {
                x = cx + cos(rot).toFloat() * outerRadius
                y = cy + sin(rot).toFloat() * outerRadius
                lineTo(x, y)
                rot += step

                x = cx + cos(rot).toFloat() * innerRadius
                y = cy + sin(rot).toFloat() * innerRadius
                lineTo(x, y)
                rot += step
            }
            lineTo(cx, cy - outerRadius)

            close()
        }

        return Outline.Generic(path)
    }

}