package com.heybik.easygradient.sample

import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import com.heybik.kotlin.easygradient.view.GradientBaseView


/**
 * custom round corner gradient view (use google logo's color)
 * Created by gaobiaoqing on 2017/12/24.
 */
class GoogleGradientView : GradientBaseView {

    var roundX: Float = 12f
    var roundY = 12f
    private val rect = RectF()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun onDraw(canvas: Canvas) {
        Log.i(TAG, "onDraw")
        rect.left = 0f
        rect.top = 0f
        rect.right = measuredWidth.toFloat()
        rect.bottom = measuredHeight.toFloat()
        canvas.drawRoundRect(rect, roundX, roundY, paint)
    }
}