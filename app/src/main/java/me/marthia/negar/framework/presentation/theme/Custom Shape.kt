package me.marthia.negar.framework.presentation.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class QuatrefoilShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {

            val centerX = size.width / 2
            val centerY = size.height / 2
            val startX = size.width * 0.35f
            val startY = size.height * 0.35f

            moveTo(startX , startY)

            quadraticBezierTo(
                x1 = centerX,
                x2 = size.width - startX,
                y1 = 0f,
                y2 = startY
            )

            quadraticBezierTo(
                x1 = size.width,
                x2 = size.width - startX ,
                y1 = centerY,
                y2 = size.height - startY
            )
            quadraticBezierTo(
                x1 = centerX,
                x2 = startX,
                y1 = size.height,
                y2 = size.height - startY
            )
            quadraticBezierTo(
                x1 = 0f,
                x2 = startX,
                y1 = centerY,
                y2 = startY
            )

            close()

        }
        return Outline.Generic(path)
    }
}
