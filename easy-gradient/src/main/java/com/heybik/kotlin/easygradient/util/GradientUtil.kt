package com.heybik.kotlin.easygradient.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import com.heybik.kotlin.easygradient.R
import org.jetbrains.annotations.NotNull
import java.util.*

/**
 * analyze gradient 's attr
 * Created by gaobiaoqing on 2017/12/10.
 */

class GradientUtil {
    private val TAG: String = "GradientUtil"

    //positions array max length
    private val POS_MAX_LENGTH: Int = 11

    private val TILE_MODE_CLAMP: String = "CLAMP"
    private val TILE_MODE_MIRROR: String = "MIRROR"
    private val TILE_MODE_REPEAT: String = "REPEAT"

    private val TYPE_LINEAR: String = "linear"
    private val TYPE_RADIAL: String = "radial"
    private val TYPE_SWEEP: String = "sweep"


    private var linearGradient: LinearGradient? = null
    private var radialGradient: RadialGradient? = null
    private var sweepGradient: SweepGradient? = null

    //gradient start x,y
    private var startX: Int = 0
    private var startY: Int = 0
    //gradient end x,y
    private var endX: Int = 0
    private var endY: Int = 0
    //gradient center x,y specific for RadialGradient & SweepGradient
    private var centerX: Int = 0
    private var centerY: Int = 0
    //radius specific for RadialGradient
    private var radius: Int = 0

    private var tileMode: String = ""
    private var type: String? = null

    fun handleAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): Paint? {
        val a: TypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.gradientLayout, defStyleAttr, 0)
        val height: Int = context.getResources().getDisplayMetrics().heightPixels
        val width = context.getResources().getDisplayMetrics().widthPixels
        startX = a.getInt(R.styleable.gradientLayout_startX, 0)
        startY = a.getInt(R.styleable.gradientLayout_startY, 0)
        endX = a.getInt(R.styleable.gradientLayout_endX, width)
        endY = a.getInt(R.styleable.gradientLayout_endY, height)
        centerX = a.getInt(R.styleable.gradientLayout_centerX, width / 2)
        centerY = a.getInt(R.styleable.gradientLayout_centerY, height / 2)
        radius = a.getInt(R.styleable.gradientLayout_radius, 0)

        val attrTileMode = a.getString(R.styleable.gradientLayout_tileMode)
        val attrType = a.getString(R.styleable.gradientLayout_gradientType)

        if (radius < 0) {
            throw  IllegalArgumentException("GradientUtil: error - radius must >= 0")
        }

        val valuesResId: Int = a.getResourceId(R.styleable.gradientLayout_gradientColors, 0)
        if (valuesResId <= 0) {
            throw  IllegalArgumentException("GradientUtil: error - colors is not specified")
        }
        //gradient colors array
        val colors = a.resources.getIntArray(valuesResId)

        val posResId: Int = a.getResourceId(R.styleable.gradientLayout_gradientPositions, 0)
        if (posResId <= 0) {
            throw  IllegalArgumentException("GradientUtil: error - positions is not specified")
        }
        //positions can be null or must has same length with colors array's length
        //0 <= position <= 1
        val positions = a.resources.getStringArray(posResId)

        handleTileModeAndType(attrTileMode, attrType)

        //array.xml can't support float-array so use string array
        val realPositions = handleColorsAndPositions(colors, positions)

        val paint = initGradientByAttr(colors, realPositions)
        a.recycle()
        return paint
    }


    fun handleTileModeAndType(attrTileMode: String?, attrType:String?) {
        if (attrTileMode == null || (!TILE_MODE_CLAMP.equals(attrTileMode, true)
                && !TILE_MODE_MIRROR.equals(attrTileMode, true)
                && !TILE_MODE_REPEAT.equals(attrTileMode, true))) {
            tileMode = TILE_MODE_CLAMP
        } else {
            tileMode = attrTileMode.toUpperCase()
        }

        if (TextUtils.isEmpty(attrType)) {
            type = TYPE_LINEAR
        } else {
            type = attrType
        }

        if (!TYPE_LINEAR.equals(type, true)
                && !TYPE_RADIAL.equals(type, true)
                && !TYPE_SWEEP.equals(type, true)) {
            throw IllegalArgumentException("GradientUtil: error - gradient type is not specified")
        }
    }

    fun handleColorsAndPositions(@NotNull colors: IntArray, @NotNull positions: Array<String>): FloatArray {
        if (colors.size < 2) {
            throw IllegalArgumentException("GradientUtil: error - colors length must >= 2")
        }

        if (positions.size != colors.size) {
            throw IllegalArgumentException("GradientUtil: error - positions length should same as colors")
        }
        if (positions.size > POS_MAX_LENGTH) {
            throw IllegalArgumentException("GradientUtil: error - positions length must less than 11")
        }
        val realPositions = FloatArray(positions.size)
        for (i in positions.indices) {
            try {
                realPositions[i] = java.lang.Float.valueOf(positions[i])
                Log.i(TAG, "realPositions[" + i + "]=" + realPositions[i])
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                throw IllegalArgumentException("GradientUtil: error - every position must be a float number")
            }
        }
        //sort positions
        Arrays.sort(realPositions)
        if (realPositions[0] < 0f) {
            throw IllegalArgumentException("GradientUtil: error - first position must >= 0")
        }
        if (realPositions[realPositions.size - 1] > 1f) {
            throw IllegalArgumentException("GradientUtil: error - last position must <= 1")
        }
        return realPositions
    }

    private fun initGradientByAttr(@NotNull colors: IntArray, @NotNull realPositions: FloatArray): Paint {
        val paint = Paint()
        val mode: Shader.TileMode = Shader.TileMode.valueOf(tileMode)
        when (type) {
            TYPE_LINEAR -> {
                linearGradient = LinearGradient(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), colors, realPositions, mode)
                paint.shader = (linearGradient)
            }
            TYPE_RADIAL -> {
                radialGradient = RadialGradient(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), colors, realPositions, mode)
                paint.shader = (radialGradient)
            }
            else -> {
                sweepGradient = SweepGradient(centerX.toFloat(), centerY.toFloat(), colors, realPositions)
                paint.shader = (sweepGradient)
            }

        }

        return paint
    }
}