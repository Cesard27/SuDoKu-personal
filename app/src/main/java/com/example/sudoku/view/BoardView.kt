package com.example.sudoku.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint
import android.graphics.Color
import android.view.MotionEvent
import com.example.sudoku.databinding.ActivityMainBinding

class BoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var sqrtSize = 3
    private var size = 9

    private var cellSizePixels = 0F

    private var selectedRow = -1
    private var selectedCol = -1

    private var listener: BoardView.OnTouchListener? = null

    //private lateinit var binding: ActivityMainBinding

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.GRAY
        strokeWidth = 2F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#999999")
        strokeWidth = 4F
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#6EAD3A")
    }

    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#D3ECC7")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }
    // divide el tamaño sobre 9 para generar la matriz 9*9
    override fun onDraw(canvas: Canvas){
        cellSizePixels = (width/size).toFloat()
        fillsCells(canvas)
        drawLines(canvas)
    }

    private fun fillsCells(canvas: Canvas){
        if (selectedCol == -1 || selectedRow == -1) return

        for (rows in 0..size){
            for (columns in 0..size){
                if (rows == selectedRow && columns == selectedCol){
                    // si es la seleccionada
                    fillCell(canvas, rows, columns, selectedCellPaint)
                } else if (rows == selectedRow || columns == selectedCol){
                    // columnas y fila que estan alineadas con la seleccionadas
                    fillCell(canvas, rows, columns, conflictingCellPaint)
                } else if ( rows / sqrtSize == selectedRow / sqrtSize && columns / sqrtSize == selectedCol / sqrtSize){
                    // region de 3*3 donde esta selecionado
                    fillCell(canvas, rows, columns, conflictingCellPaint)
                }
            }
        }
    }

    private fun fillCell(canvas: Canvas, row: Int, colum: Int, paint: Paint){
        canvas.drawRect(
            colum * cellSizePixels,
            row * cellSizePixels,
            (colum + 1) * cellSizePixels,
            (row + 1) * cellSizePixels,
            paint
        )
    }

    private fun drawLines(canvas: Canvas){
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)

        for (lines in 1 until size){
            val paintToUse = when (lines % sqrtSize) {
                0 -> thinLinePaint
                else -> thickLinePaint
            }
            // Vertical
            canvas.drawLine(
                lines * cellSizePixels,
                0F,
                lines * cellSizePixels,
                height.toFloat(),
                paintToUse
            )
            //Horizontal
            canvas.drawLine(
                0F,
                lines * cellSizePixels,
                width.toFloat(),
                lines * cellSizePixels,
                paintToUse
            )

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float){
        val possSelectedCol = (x / cellSizePixels).toInt()
        val possSelectedRow = (y / cellSizePixels).toInt()
        listener?.onCellTouched(possSelectedRow, possSelectedCol)
    }

    fun updateSelectedCellUI(row: Int, colum: Int) {
        selectedRow = row
        selectedCol = colum
        invalidate()
    }

    fun registerListener(listener: BoardView.OnTouchListener){
        this.listener = listener
    }

    interface OnTouchListener {
        fun onCellTouched(row: Int, colum: Int)
    }

}