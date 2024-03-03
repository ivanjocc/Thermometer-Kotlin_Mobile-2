package com.example.thermo_kotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class Thermometre(context: Context, private var temperature: Float = 0.0F) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    fun updateTemperature(newTemperature: Float) {
        temperature = newTemperature
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = width / 10f
        val strokeWidth = width / 50f
        val margin = radius + strokeWidth

        // Set paint stroke width here
        paint.strokeWidth = strokeWidth

        // Adjust paint properties based on temperature
        adjustPaintForTemperature(temperature)

        // Calculate positions based on temperature
        val tempRatio = (temperature.coerceIn(-20F, 40F) + 20) / 60
        val yStart = (1 - tempRatio) * (height - margin) + margin
        val yEnd = height - margin
        val centerX = width / 2f

        // Draw the thermometer line and bottom circle
        canvas.drawLine(centerX, yStart, centerX, yEnd, paint)
        canvas.drawCircle(centerX, yEnd, radius, paint)
    }

    private fun adjustPaintForTemperature(temp: Float) {
        val tempNormalized = temp.coerceIn(-20F, 40F)
        val tempRatio = (tempNormalized + 20) / 60
        paint.color = calculateColorForTemperature(tempRatio)
    }

    private fun calculateColorForTemperature(tempRatio: Float): Int {
        val red = (255 * tempRatio).toInt()
        val blue = 255 - red
        val green = (128 - kotlin.math.abs(tempRatio - 0.5) * 256).toInt()
        return android.graphics.Color.rgb(red, green, blue)
    }
}
