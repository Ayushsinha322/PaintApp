package com.dangerx.paintapp

// PaintView.kt

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PaintView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint()
    private var path = Path()
    private var currentColor = Color.BLACK
    private var currentBrushSize = 10f
    private val drawHistory = mutableListOf<Path>()

    init {
        setupPaint()
    }

    private fun setupPaint() {
        paint.color = currentColor
        paint.isAntiAlias = true
        paint.strokeWidth = currentBrushSize
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
    }

    fun setColor(color: Int) {
        currentColor = color
        setupPaint()
    }

    fun setBrushSize(brushSize: Float) {
        currentBrushSize = brushSize
        setupPaint()
    }

    fun undo() {
        if (drawHistory.isNotEmpty()) {
            drawHistory.removeAt(drawHistory.size - 1)
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        for (drawnPath in drawHistory) {
            canvas.drawPath(drawnPath, paint)
        }
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path = Path()
                path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                drawHistory.add(Path(path))
                path.reset()
            }
            else -> return false
        }

        invalidate()
        return true
    }
}