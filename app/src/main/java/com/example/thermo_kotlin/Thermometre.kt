package com.example.thermo_kotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Thermometre(context: Context, private var temp: Float = 0.0F) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        // Increase the text size for the numbers
        textSize = width / 12f * 1.2f
    }
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    fun updateTemperature(newTemperature: Float) {
        temp = newTemperature.coerceIn(0f, 100f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Dimensions and adjustments based on the view's width
        val radius = width / 12f
        val strokeWidth = width / 50f
        val margin = radius * 2
        textPaint.textSize = radius * 1.2f
        linePaint.strokeWidth = strokeWidth / 2

        // Normalize temperature and adjust paint properties
        val tempRatio = temp / 100
        adjustPaintForTemperature(tempRatio)

        // Calculate positions
        val yStart = (height - margin) * (1 - tempRatio) + margin / 2
        val yEnd = height - margin / 2
        val centerX = width / 2f

        // Draw the thermometer's mercury line and bulb
        canvas.drawLine(centerX, yStart, centerX, yEnd, paint)
        canvas.drawCircle(centerX, yEnd, radius, paint)

        // Draw side markings and numbers
        for (i in 0..10) {
            val yMark = (height - margin) * (1 - i / 10f) + margin / 2
            canvas.drawLine(centerX - radius * 1.5f, yMark, centerX - radius * 2.5f, yMark, linePaint)
            canvas.drawLine(centerX + radius * 1.5f, yMark, centerX + radius * 2.5f, yMark, linePaint)
            canvas.drawText("${i * 10}", centerX - radius * 5f, yMark + radius / 4, textPaint)
        }
    }

    private fun adjustPaintForTemperature(tempRatio: Float) {
        val red = (255 * tempRatio).toInt()
        val blue = 255 - red
        val green = (128 - kotlin.math.abs(tempRatio - 0.5) * 256).toInt()
        paint.color = Color.rgb(red, green, blue)
        paint.strokeWidth = width / 50f
    }
}
