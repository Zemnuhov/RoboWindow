package com.robo.window.ui

import android.R.attr
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.R.attr.radius
import android.annotation.SuppressLint

import android.graphics.RectF
import android.text.Layout
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.robo.window.R


class ScaleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect: RectF by lazy { RectF(0.0F,
        height/2-(width/9).toFloat()/2,
        (width/9).toFloat(),
        height/2+(width/9).toFloat()/2) }
    private val rect1: RectF by lazy { RectF(rect.right,
        rect.top,
        rect.right+rect.width(),
        rect.bottom) }
    private val rect2: RectF by lazy { RectF(rect1.right,
        rect1.top,
        rect1.right+rect1.width(),
        rect1.bottom) }
    private val rect3: RectF by lazy { RectF(rect2.right,
        rect2.top,
        rect2.right+rect2.width(),
        rect2.bottom) }
    private val rect4 : RectF by lazy { RectF(rect3.right,
        rect3.top,
        rect3.right+rect3.width(),
        rect3.bottom) }
    private val rect5 : RectF by lazy { RectF(rect4.right,
        rect4.top,
        rect4.right+rect4.width(),
        rect4.bottom) }
    private val rect6 : RectF by lazy { RectF(rect5.right,
        rect5.top,
        rect5.right+rect5.width(),
        rect5.bottom) }
    private val rect7 : RectF by lazy { RectF(rect6.right,
        rect6.top,
        rect6.right+rect6.width(),
        rect6.bottom) }
    private val rect8 : RectF by lazy { RectF(rect7.right,
        rect7.top,
        rect7.right+rect7.width(),
        rect7.bottom) }

    var interval: Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = 500
        val desiredHeight = 75

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //Measure Width

        //Measure Width
        width = if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            desiredWidth
        }

        //Measure Height

        //Measure Height
        height = if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            Math.min(desiredHeight, heightSize)
        } else {
            //Be whatever you want
            desiredHeight
        }

        //MUST CALL THIS

        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.layoutParams = ViewGroup.LayoutParams(
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))

        paint.color = if(interval>10){
            Color.GREEN
        }else{
            ContextCompat.getColor(context, R.color.not_active_green)
        }

        canvas.drawRect(rect,paint)
        paint.color = if(interval>20){
            Color.GREEN
        }else{
            ContextCompat.getColor(context, R.color.not_active_green)
        }
        canvas.drawRect(rect1,paint)
        paint.color = if(interval>30){
            Color.GREEN
        }else{
            ContextCompat.getColor(context, R.color.not_active_green)
        }

        canvas.drawRect(rect2,paint)
        paint.color = ContextCompat.getColor(context, R.color.not_active_yellow)
        canvas.drawRect(rect3,paint)
        canvas.drawRect(rect4,paint)
        canvas.drawRect(rect5,paint)
        paint.color = ContextCompat.getColor(context, R.color.not_active_red)
        canvas.drawRect(rect6,paint)
        canvas.drawRect(rect7,paint)
        canvas.drawRect(rect8,paint)
    }

    fun setState(value: Int){
        interval = value
        invalidate()
    }
}