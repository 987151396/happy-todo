package com.happy.todo.weight

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.happy.todo.R
import com.happy.todo.lib_common.utils.ImageUtil

/**
 * Author by Ouyangle, Date on 2022/4/12.
 * PS: Not easy to write code, please indicate.
 */
class WindowCurtainsView : View, android.view.GestureDetector.OnGestureListener{
    private var curtainPaint1 = Paint(Paint.ANTI_ALIAS_FLAG)
    private var curtainPaint2 = Paint(Paint.ANTI_ALIAS_FLAG)

    private var unit1 = dp2px(6f)
    private var unit2 = dp2px(9f)

    private var bar1RectF = RectF()
    private var bar2RectF = RectF()

    private var wight = 0f
    private var height = 0f
    private var progress = 0.5f

    private var bitmapBar : Bitmap? = null

    companion object{
        const val MODE_LEFT = 1
        const val MODE_RIGHT = 2
        const val MODE_TOP_BOTTOM = 3
    }
    private var viewMode = MODE_TOP_BOTTOM

    // 定义手势检测器实例
    private var detector: GestureDetector? = null

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        curtainPaint1.color = context?.resources?.getColor(R.color.white_E7ECEF)!!
        curtainPaint2.color = context.resources?.getColor(R.color.white_D2DEE5)!!

        bitmapBar = ImageUtil.drawable2Bitmap(context.resources.getDrawable(R.mipmap.icon_bar))

        detector = GestureDetector(context,this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        wight = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        setMeasuredDimension(wight.toInt(), height.toInt())
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.YELLOW)
        when(viewMode){
            MODE_LEFT ->{
                var curtainRectF = RectF(0f,0f,wight, height)
                draw1_2(canvas, curtainRectF)
            }
            MODE_RIGHT ->{
                var curtainRectF = RectF(0f,0f,wight, height)
                draw1_2(canvas, curtainRectF)
            }
            MODE_TOP_BOTTOM ->{
                var curtainRectF = RectF(0f,0f,wight, height)
                var clipRect = RectF(curtainRectF.left,curtainRectF.top,curtainRectF.right, curtainRectF.bottom * progress)


                bar1RectF.left = clipRect.left + (clipRect.right - clipRect.left) / 2 - bitmapBar?.width!! / 2
                bar1RectF.right = clipRect.left + (clipRect.right - clipRect.left) / 2 + bitmapBar?.width!! / 2
                bar1RectF.top = clipRect.bottom - bitmapBar?.height!! / 2
                bar1RectF.bottom = clipRect.bottom + bitmapBar?.height!! / 2

                if(bar1RectF.bottom >= height){
                    bar1RectF.top = height - bitmapBar?.height!!
                    bar1RectF.bottom = height
                }

                if(bar1RectF.top <= curtainRectF.top){
                    bar1RectF.top = curtainRectF.top
                    bar1RectF.bottom = curtainRectF.top + bitmapBar?.height!!
                }

                canvas.drawBitmap(bitmapBar!!,bar1RectF.left, bar1RectF.top, Paint())
                draw3(canvas, curtainRectF,clipRect)
                canvas.drawBitmap(bitmapBar!!,bar1RectF.left, bar1RectF.top, Paint())
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector?.onTouchEvent(event)!!
    }

    override fun onDown(e: MotionEvent): Boolean {
        return e.x >= bar1RectF.left && e.x <= bar1RectF.right && e.y >= bar1RectF.top && e.y <= bar1RectF.bottom
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        e2?.let { moveProgressBar(e2.y) }
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    private fun moveProgressBar(value: Float) {
        var rate = 1f
        when(viewMode){
            MODE_LEFT->{

            }
            MODE_RIGHT->{

            }
            MODE_TOP_BOTTOM->{
                rate = value / height
                setProgress(rate)
            }
        }
    }

    public fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    private fun draw3(canvas: Canvas, curtainRectF: RectF, clipRect: RectF) {
        canvas.clipRect(clipRect)

        var raduis = dp2px(15f)
        var step = 23
        var h : Float = (curtainRectF.bottom - curtainRectF.top) / step

        var getStep = step / 2
        if(step % 2 != 0){
            getStep = (step - 1) / 2
        }
        for (index: Int in 0 until getStep) {
            //双数的占比
            var rote : Float = unit1.toFloat() / (unit1.toFloat() + unit2.toFloat())
            //双数
            var top : Float = curtainRectF.top + h * 2 * index
            var rf1 = RectF(curtainRectF.left, top,curtainRectF.right, top + h * 2 * rote)
            canvas.drawRect(rf1,curtainPaint1)
            //单数
            var rf2 = RectF(curtainRectF.left, rf1.bottom,curtainRectF.right, top + h * 2)
            var path = Path()
            path.moveTo(rf2.left,rf2.top)
            path.cubicTo(rf2.left,rf2.top,rf2.left + raduis, rf2.top + (rf2.bottom - rf2.top) / 2, rf2.left,rf2.bottom)
            path.lineTo(rf2.right,rf2.bottom)
            path.cubicTo(rf2.right,rf2.bottom,rf2.right - raduis, rf2.top + (rf2.bottom - rf2.top) / 2, rf2.right,rf2.top)
            path.lineTo(rf2.left,rf2.top)
            canvas.drawPath(path,curtainPaint2)
        }

        //如果有余数
        if(step % 2 != 0){
            var top : Float = curtainRectF.top + h * (step - 1)
            var rf1 = RectF(curtainRectF.left, top,curtainRectF.right, top + h)
            canvas.drawRect(rf1,curtainPaint1)
        }
    }

    /*private fun draw3(canvas: Canvas, curtainRectF: RectF) {
        var raduis = dp2px(5f)
        var step = 23
        var h : Float = (curtainRectF.bottom - curtainRectF.top) / step

        for (index: Int in 0 until step) {
            var top : Float = curtainRectF.top + h * index
            if (index % 2 == 0) {
                var rf = RectF(curtainRectF.left, top,curtainRectF.right, top + h)
                canvas.drawRect(rf,curtainPaint1)
            } else {
                var rf = RectF(curtainRectF.left, top,curtainRectF.right, top + h)
                var path = Path()
                path.moveTo(rf.left,rf.top)
                path.cubicTo(rf.left,rf.top,rf.left + raduis, rf.top + (rf.bottom - rf.top) / 2, rf.left,rf.bottom)
                path.lineTo(rf.right,rf.bottom)
                path.cubicTo(rf.right,rf.bottom,rf.right - raduis, rf.top + (rf.bottom - rf.top) / 2, rf.right,rf.top)
                path.lineTo(rf.left,rf.top)
                canvas.drawPath(path,curtainPaint2)
            }
        }
    }*/

    private fun draw1_2(canvas: Canvas, curtainRectF: RectF) {
        var raduis = dp2px(5f)
        var step = 16
        var w : Float = (curtainRectF.right - curtainRectF.left) / step
        for (index: Int in 0 until step) {
            var left : Float = curtainRectF.left + index * w
            if (index % 2 == 0) {
                if (viewMode == MODE_LEFT) {
                    drawCurtains1(canvas, left, index, raduis, w)
                }else{
                    drawCurtains2(canvas, left, index, raduis, w)
                }
            } else {
                if (viewMode == MODE_LEFT) {
                    drawCurtains2(canvas, left, index, raduis, w)
                }else{
                    drawCurtains1(canvas, left, index, raduis, w)
                }
            }
        }
    }

    private fun drawCurtains1(canvas: Canvas, left: Float, index: Int, raduis: Int, w: Float) {
        var rf = RectF(left, 0f, left + w, height)
        var path = Path()
        path.moveTo(rf.left, rf.top)
        path.lineTo(rf.left, rf.bottom - raduis)
        path.lineTo(rf.right, rf.bottom - raduis)
        path.lineTo(rf.right, rf.top)

        var path2 = Path()
        var rf2 = RectF(left, rf.bottom - raduis, left + w, rf.bottom)
        path2.moveTo(rf2.left, rf2.top)
        path2.cubicTo(
            rf2.left,
            rf2.top,
            rf2.left + (rf2.right - rf2.left) / 2,
            rf2.bottom,
            rf2.right,
            rf2.top
        )

        path.addPath(path2)

        //var clipRect = RectF(curtainRectFLeft.left,0f,curtainRectFLeft.right / 2f, curtainRectFLeft.bottom)
        //canvas.clipRect(clipRect)

        canvas.drawPath(path, curtainPaint1)
    }

    private fun drawCurtains2(canvas: Canvas, left: Float, index: Int, raduis: Int, w: Float) {
        var rf = RectF(left, 0f, left + w, height)
        var path = Path()
        path.moveTo(rf.left, rf.top)

        var rf2 =
            RectF(left, rf.bottom - raduis * 2, left + w, rf.bottom - raduis)
        path.lineTo(rf2.left, rf2.bottom)
        path.cubicTo(
            rf2.left,
            rf2.bottom,
            rf2.left + (rf2.right - rf2.left) / 2,
            rf2.top,
            rf2.right,
            rf2.bottom
        )

        path.lineTo(rf.right, rf.top)
        path.lineTo(rf.left, rf.top)

        path.addPath(path)
        canvas.drawPath(path, curtainPaint2)
    }

    /**
     * sp 转 px
     *
     * @param spValue sp 值
     * @return px 值
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }



    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }
    override fun onLongPress(e: MotionEvent?) {
    }
}