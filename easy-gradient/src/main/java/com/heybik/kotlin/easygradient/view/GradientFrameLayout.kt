package com.heybik.kotlin.easygradient.view

import android.widget.FrameLayout
import com.heybik.kotlin.easygradient.util.GradientUtil
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log


/**
 *
 * Created by gaobiaoqing on 2017/12/23.
 */

open class GradientFrameLayout : FrameLayout {

    private val TAG = "GradientFrameLayout"
    var paint: Paint? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val util = GradientUtil()
        paint = util.handleAttrs(context, attrs, defStyleAttr)
        setWillNotDraw(false)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        val util = GradientUtil()
        paint = util.handleAttrs(context, attrs, defStyleAttr)
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.i(TAG, "onDraw")
        canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), paint)
    }
}