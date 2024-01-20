package com.dangerx.paintapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.dangerx.paintapp.MainActivity.Companion.paintBrush

class PaintView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val pathList = ArrayList<Path>()
    private val colorList = ArrayList<Color>()

    init {
        initPaintBrush()
    }

    private fun initPaintBrush() {
        paintBrush.isAntiAlias = true
        paintBrush.color = Color.Black.toColorInt() // Convert Color to Int
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeCap = Paint.Cap.ROUND
        paintBrush.strokeWidth = 10f // Adjust stroke width as needed
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in pathList.indices) {
            paintBrush.color = colorList[i].toColorInt() // Convert Color to Int
            canvas.drawPath(pathList[i], paintBrush)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
            }
        }
        invalidate()
        return true
    }

    private fun touchStart(x: Float, y: Float) {
        val path = Path()
        path.moveTo(x, y)
        pathList.add(path)
        colorList.add(Color(paintBrush.color)) // Convert Int to Color
    }

    private fun touchMove(x: Float, y: Float) {
        pathList.last().lineTo(x, y)
    }

    private fun touchUp() {
        // Add any necessary cleanup after touch up
    }

    fun setColor(newColor: Color) {
        paintBrush.color = newColor.toColorInt() // Convert Color to Int
    }
}

private fun Color.toColorInt(): Color {

}
