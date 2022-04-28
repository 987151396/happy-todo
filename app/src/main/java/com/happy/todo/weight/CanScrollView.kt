package com.happy.todo.weight

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.ScaleAnimation
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.ViewPager
import com.happy.todo.R
import com.happy.todo.lib_common.utils.SizeUtil
import com.happy.todo.lib_common.utils.SpanUtil
import com.happy.todo.lib_common.utils.ToastUtil
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Author by Ouyangle, Date on 2022/1/19.
 * PS: Not easy to write code, please indicate.
 */
class CanScrollView : HorizontalScrollView{
    private var logTag : String  = "CanScrollView"
    private var mContainer : LinearLayout? = null
    private var mContext: Context? = null
    private var mCurrentTab = 0
    //
    private var newScrollX = 0
    private var newScrollY = 0
    private var oldScrollX = 0
    private var oldScrollY = 0
    //
    private var isAutoPlay = false
    private var playStep = 5
    private var isLoop = false
    private var delayMillis = 1000L
    private val LOOP_CURRENT_TAB = 0x0a
    private var scrollView_right = 0

   //标准模式
    val MODE_STANDARD = 0xfa
    //堆叠模式
    val MODE_STACK = 0xfb
    private var viewBetweenWidth = 0f

    //堆叠模式2--- 选中的放大，两边小，选中的叠在另外左右两边的布局之上
    val MODE_STACK2 = 0xfc

    private var view_mode = MODE_STACK2

    private var rootViewHeight = 0

    private val myHandle by lazy{
        @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(@NonNull msg: Message) {
                when(msg.what){
                    LOOP_CURRENT_TAB ->{
                        mCurrentTab++
                        if(mCurrentTab > mContainer?.childCount!! - 1){
                            mCurrentTab = 0
                        }
                        scrollToCurrentTab()
                        toLoop()
                    }
                }
            }
        }
    }
    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        isFillViewport = true //设置滚动视图是否可以伸缩其内容以填充视口
        setWillNotDraw(false) //重写onDraw方法,需要调用这个方法来清除flag
        clipChildren = false
        clipToPadding = false

        this.mContext = context
        mContainer = LinearLayout(context)
        mContainer?.clipChildren = false
        mContainer?.clipToPadding = false
        mContainer?.gravity = Gravity.CENTER_VERTICAL

        obtainAttributes(context, attrs)
        init()
    }

    private fun init() {
        initViewMode()
        addView(mContainer)
        addContainerView()
        selectedCurrent()
        if(isAutoPlay){
            toAutoPlayScroll()
        }
    }

    private fun obtainAttributes(context : Context?, attrs : AttributeSet?) {

    }

    private fun initViewMode() {
        when(view_mode){
            MODE_STANDARD->{
            }
            MODE_STACK->{
                viewBetweenWidth = SizeUtil.dp2px(60f) * 2f
            }
            MODE_STACK2->{
                viewBetweenWidth = SizeUtil.dp2px(130f) * 2f
                mCurrentTab = 2
            }
        }
    }

    private fun addContainerView() {
        mContainer?.removeAllViews()
        var itemView: View
        for(a in 0..6){
            itemView = inflate(mContext, R.layout.layout_canscrollview, null)
            addTab(a, itemView)
        }
    }

    private fun addTab(position: Int, itemView: View?) {
        itemView?.post {
            scrollView_right = itemView?.left!!
            Log.d(logTag,"scrollView_right : " + scrollView_right)
        }
        var tvTitle = itemView?.findViewById<TextView>(R.id.tv_title)
        var iv_img = itemView?.findViewById<ImageView>(R.id.iv_img)
        tvTitle?.text = "position : $position"

        iv_img?.layoutParams = LinearLayout.LayoutParams(getScreenWidth(), LayoutParams.MATCH_PARENT)

        itemView?.setOnClickListener {
            if(isAutoPlay){
                ToastUtil.showShort("position : $position")
                return@setOnClickListener
            }
            if(isLoop){
                ToastUtil.showShort("position : $position")
                return@setOnClickListener
            }

            val pos: Int = mContainer?.indexOfChild(it)!!
            if (pos != -1) {
                mCurrentTab = pos
                scrollToCurrentTab()
            }
        }

        //var lp : LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f)
        var lp = LinearLayout.LayoutParams((getScreenWidth() - viewBetweenWidth).toInt(), LayoutParams.MATCH_PARENT)
        if(view_mode == MODE_STACK2){
            lp = LinearLayout.LayoutParams((getScreenWidth() - viewBetweenWidth).toInt(), SizeUtil.dp2px(150f))
        }
        mContainer?.addView(itemView, position, lp)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        rootViewHeight = measuredHeight
    }

    private val autoPlayRunnable by lazy {
        var step_offset = 0

            Runnable{
                run {
                    if(isAutoPlay){
                        smoothScrollBy(step_offset,0)
                        step_offset += playStep
                        if(step_offset > scrollView_right){
                            step_offset = 0
                        }
                        Log.d(logTag,"autoPlayRunnable : " + step_offset + " >>>>> right : " + scrollView_right)
                        Thread.sleep(5)
                        smoothScrollTo(step_offset,0)
                        toAutoPlayScroll()
                    }
                }
            }
    }

    private fun toAutoPlayScroll(){
        myHandle.postDelayed(autoPlayRunnable,0)
    }

    private fun toLoop(){
        myHandle.sendEmptyMessageDelayed(LOOP_CURRENT_TAB,delayMillis)
    }

    private fun stopPlayScroll(){
        myHandle.removeCallbacks(autoPlayRunnable)
    }

    private fun stopLoop(){
        myHandle.removeMessages(LOOP_CURRENT_TAB)
    }

    public fun isAutoPlay(b : Boolean){
        this.isAutoPlay = b
        if (isAutoPlay) {
            isLoop(false)
            toAutoPlayScroll()
        }else{
            stopPlayScroll()
        }
    }

    public fun isAutoPlay() : Boolean{
        return isAutoPlay
    }

    public fun isLoop(b : Boolean){
        this.isLoop = b
        if(isLoop){
            isAutoPlay(false)
            toLoop()
        }else{
            stopLoop()
        }
    }

    public fun setViewMode(m : Int){
        this.view_mode = m
        invalidate()
    }

    public fun isLoop() : Boolean{
        return isLoop
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_DOWN ->{
                if(isLoop){
                    stopLoop()
                }
            }
            MotionEvent.ACTION_MOVE ->{
                if (isAutoPlay) {
                    return isAutoPlay
                }
            }
            MotionEvent.ACTION_UP ->{
                if(isLoop){
                    toLoop()
                }
                if(!isAutoPlay) {
                    return selectedCurrent()
                }

            }
        }
        return super.onTouchEvent(ev)
    }

    private fun selectedCurrent() : Boolean {
        if(newScrollX >= oldScrollX){ //right
            for (a in mCurrentTab until mContainer?.childCount!!){
                if(scrollX > mContainer?.getChildAt(a)?.left?.plus(mContainer?.getChildAt(a)?.width!! / 5 * 1)!!){
                    mCurrentTab = a + 1
                    scrollToCurrentTab()
                    break
                }else {
                    mCurrentTab = a
                    scrollToCurrentTab()
                    break
                }
            }
        }else if(newScrollX <  oldScrollX){//left
            for (a in mCurrentTab until mContainer?.childCount!!){
                if(scrollX < mContainer?.getChildAt(a)?.left!! - (mContainer?.getChildAt(a)?.width!! / 5 * 1)){
                    mCurrentTab = a - 1
                    scrollToCurrentTab()
                    break
                }else {
                    mCurrentTab = a
                    scrollToCurrentTab()
                    break
                }
            }
        }
        return false
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        this.newScrollX = l
        this.newScrollY = t
        this.oldScrollX = oldl
        this.oldScrollY = oldt
        effect_modeStack2()
    }

    private fun effect_modeStack2() {
        for (a in 0 until mContainer?.childCount!!){

            var max : Float = viewBetweenWidth / 2

            var distance = Math.min(getDistanceBetween2Points(PointF(newScrollX.toFloat(),
                0f
            ), PointF( mContainer?.getChildAt(a)?.left!!.toFloat() - max,
                0f
            )), max)

            var x = mContainer?.getChildAt(a)?.left!!.toFloat()
            var fraction: Float = (1.0f * distance) / max
            fraction = 1f + (1f- fraction) / 3f * 1f
            Log.d(logTag, "x $x >>>>>> max : $max >>>>>> fraction : $fraction >>>>>> a : $a")
            mContainer?.getChildAt(a)?.elevation = if(fraction > 1){
                1f
            }else{
                0f
            }
            mContainer?.getChildAt(a)?.scaleX = fraction
            mContainer?.getChildAt(a)?.scaleY = fraction
        }
    }

    private fun scrollToCurrentTab(){
        when(view_mode){
            MODE_STANDARD ->{
                var offset = mContainer?.getChildAt(mCurrentTab)?.width!! * mCurrentTab
                smoothScrollTo(offset,0)
            }
            MODE_STACK ->{
                var offset = mContainer?.getChildAt(mCurrentTab)?.width!! * mCurrentTab - viewBetweenWidth.toInt() / 2
                smoothScrollTo(offset,0)
            }
            MODE_STACK2 ->{
                var offset = mContainer?.getChildAt(mCurrentTab)?.width!! * mCurrentTab - viewBetweenWidth.toInt() / 2
                smoothScrollTo(offset,0)
                //toScaleView()
            }
        }
    }

    private fun toScaleView(){
        var scale = 1.3f
        for (a in 0 until mContainer?.childCount!!){
            //取view中心点
            val centerX: Float =  mContainer?.getChildAt(a)?.width!! / 2f
            val centerY: Float = mContainer?.getChildAt(a)?.height?.div(2f)!!

            if(a == mCurrentTab){
                var scaleAnim =  ScaleAnimation(1f, scale, 1f,scale,centerX,centerY)
                scaleAnim.fillAfter = true
                mContainer?.getChildAt(a)?.elevation = SizeUtil.dp2px(1f).toFloat()
                mContainer?.getChildAt(a)?.startAnimation(scaleAnim)
            }else{
                var scaleAnim =  ScaleAnimation(scale, 1f, scale,1f,centerX,centerY)
                scaleAnim.fillAfter = true
                mContainer?.getChildAt(a)?.elevation = SizeUtil.dp2px(0f).toFloat()
                mContainer?.getChildAt(a)?.startAnimation(scaleAnim)
            }
        }
    }

    private fun getScreenWidth(): Int {
        val wm = mContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    fun getDistanceBetween2Points(p0: PointF, p1: PointF): Float {
        val distance =
            Math.sqrt(
                Math.pow((p0.y - p1.y).toDouble(), 2.0) + Math.pow(
                    (p0.x - p1.x).toDouble(),
                    2.0
                )
            )
                .toFloat()
        Log.d("getDistance", "distance : $distance")
        return distance
    }
}