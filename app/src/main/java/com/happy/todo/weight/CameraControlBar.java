package com.happy.todo.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ViewUtils;

import com.happy.todo.R;
import com.happy.todo.lib_common.utils.ImageUtil;
import com.happy.todo.lib_common.utils.SizeUtil;
import com.happy.todo.lib_common.utils.ViewOnClickUtils;
import com.happy.todo.lib_common.utils.ViewUtil;

/**
 *
 * PS: Not easy to write code, please indicate.
 */
public class CameraControlBar extends View {
    private int viewWidth = 0;
    private int viewHeight = 0;
    private Bitmap bgBitMap;
    private Bitmap bitMapTop;
    private Bitmap bitMapBottom;
    private Bitmap bitMapLeft;
    private Bitmap bitMapRight;

    private Context mContext;

    private Paint rectPint;

    private PointF yd_pointF = new PointF();

    private Region mRegionTop = new Region();
    private Region mRegionBottom = new Region();
    private Region mRegionLeft = new Region();
    private Region mRegionRight = new Region();

    private int[] angleTop = new int[]{225,90};
    private int[] angleBottom = new int[]{45,90};
    private int[] angleLeft = new int[]{135,90};
    private int[] angleRight = new int[]{315,90};

    public static final int ACTION_TOP = 0x0a;
    public static final int ACTION_BOTTOM = 0x0b;
    public static final int ACTION_LEFT = 0x0c;
    public static final int ACTION_RIGHT = 0x0d;

    private int touchAction = -1;

    private float raduis;

    public CameraControlBar(Context context) {
        this(context,null);
    }

    public CameraControlBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraControlBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        bgBitMap = ImageUtil.getBitmap(R.mipmap.bg_yao_gan);
        bitMapTop = ImageUtil.getBitmap(R.mipmap.ic_control_top);
        bitMapBottom = ImageUtil.getBitmap(R.mipmap.ic_control_bottom);
        bitMapLeft = ImageUtil.getBitmap(R.mipmap.ic_control_left);
        bitMapRight = ImageUtil.getBitmap(R.mipmap.ic_control_right);

        rectPint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPint.setColor(Color.parseColor("#EEF2F8"));
        rectPint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = bgBitMap.getWidth();
        viewHeight = bgBitMap.getHeight();
        raduis = (viewWidth - SizeUtil.dp2px(5f) * 2) / 2f;
        setMeasuredDimension(viewWidth,viewHeight);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bgBitMap,0,0,new Paint());

        RectF totalRect = new RectF(SizeUtil.dp2px(5f),SizeUtil.dp2px(5f),viewWidth - SizeUtil.dp2px(5f),viewHeight - SizeUtil.dp2px(5f));
        yd_pointF.set(totalRect.left + (totalRect.right - totalRect.left) / 2, totalRect.top + (totalRect.bottom - totalRect.top) / 2);

        Path topPath = new Path();
        topPath.addArc(totalRect,angleTop[0],angleTop[1]);
        onDrawStar(canvas, topPath, totalRect, ACTION_TOP);

        Path bottomPath = new Path();
        bottomPath.addArc(totalRect,angleBottom[0],angleBottom[1]);
        onDrawStar(canvas, bottomPath, totalRect, ACTION_BOTTOM);

        Path leftPath = new Path();
        leftPath.addArc(totalRect,angleLeft[0],angleLeft[1]);
        onDrawStar(canvas, leftPath, totalRect, ACTION_LEFT);

        Path rightPath = new Path();
        rightPath.addArc(totalRect,angleRight[0],angleRight[1]);
        onDrawStar(canvas, rightPath, totalRect, ACTION_RIGHT);

        int size = SizeUtil.dp2px(30f);
        RectF rect = new RectF(yd_pointF.x - size,yd_pointF.y - size,yd_pointF.x + size , yd_pointF.y + size);
        canvas.rotate(45,yd_pointF.x,yd_pointF.y);
        canvas.drawRoundRect(rect,SizeUtil.dp2px(20),SizeUtil.dp2px(20),rectPint);
    }

    private void onDrawStar(Canvas canvas, Path path, RectF totalRect, int action) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        //p.setStrokeWidth(SizeUtil.dp2px(0));
        p.setColor(Color.TRANSPARENT);

        PointF startPointF = new PointF();
        PointF endPointF = new PointF();

        RectF rf = new RectF();

        switch (action){
            case ACTION_TOP:
                //p.setColor(mContext.getResources().getColor(R.color.blue_2D91F7));
                startPointF.set(getArcX(yd_pointF.x, raduis, angleTop[0]), getArcY(yd_pointF.y, raduis, angleTop[0]));
                endPointF.set(getArcX(yd_pointF.x, raduis, angleTop[0] + angleTop[1]), getArcY(yd_pointF.y, raduis, angleTop[0] + angleTop[1]));

                path.moveTo(startPointF.x,startPointF.y);
                path.lineTo(yd_pointF.x,yd_pointF.y);
                path.lineTo(endPointF.x,endPointF.y);

                path.computeBounds(rf,true);
                mRegionTop.setPath(path,new Region((int)rf.left,(int)rf.top,(int)rf.right,(int)rf.bottom));

                if(touchAction == action){
                    canvas.drawBitmap(bitMapTop,startPointF.x,totalRect.top,new Paint());
                }
                break;
            case ACTION_BOTTOM:
                //p.setColor(mContext.getResources().getColor(R.color.yellow_E3AF1A));
                startPointF.set(getArcX(yd_pointF.x, raduis, angleBottom[0]), getArcY(yd_pointF.y, raduis, angleBottom[0]));
                endPointF.set(getArcX(yd_pointF.x, raduis, angleBottom[0] + angleBottom[1]), getArcY(yd_pointF.y, raduis, angleBottom[0] + angleBottom[1]));

                path.moveTo(startPointF.x,startPointF.y);
                path.lineTo(yd_pointF.x,yd_pointF.y);
                path.lineTo(endPointF.x,endPointF.y);

                path.computeBounds(rf,true);
                mRegionBottom.setPath(path,new Region((int)rf.left,(int)rf.top,(int)rf.right,(int)rf.bottom));

                if(touchAction == action){
                    canvas.drawBitmap(bitMapBottom,endPointF.x,totalRect.bottom - bitMapBottom.getHeight(),new Paint());
                }
                break;
            case ACTION_LEFT:
                //p.setColor(mContext.getResources().getColor(R.color.black));
                startPointF.set(getArcX(yd_pointF.x, raduis, angleLeft[0]), getArcY(yd_pointF.y, raduis, angleLeft[0]));
                endPointF.set(getArcX(yd_pointF.x, raduis, angleLeft[0] + angleLeft[1]), getArcY(yd_pointF.y, raduis, angleLeft[0] + angleLeft[1]));

                path.moveTo(startPointF.x,startPointF.y);
                path.lineTo(yd_pointF.x,yd_pointF.y);
                path.lineTo(endPointF.x,endPointF.y);

                path.computeBounds(rf,true);
                mRegionLeft.setPath(path,new Region((int)rf.left,(int)rf.top,(int)rf.right,(int)rf.bottom));

                if(touchAction == action){
                    canvas.drawBitmap(bitMapLeft,totalRect.left,endPointF.y,new Paint());
                }
                break;
            case ACTION_RIGHT:
                //p.setColor(mContext.getResources().getColor(R.color.blue_02909E));
                startPointF.set(getArcX(yd_pointF.x, raduis, angleRight[0]), getArcY(yd_pointF.y, raduis, angleRight[0]));
                endPointF.set(getArcX(yd_pointF.x, raduis, angleRight[0] + angleRight[1]), getArcY(yd_pointF.y, raduis, angleRight[0] + angleRight[1]));

                path.moveTo(startPointF.x,startPointF.y);
                path.lineTo(yd_pointF.x,yd_pointF.y);
                path.lineTo(endPointF.x,endPointF.y);

                path.computeBounds(rf,true);
                mRegionRight.setPath(path,new Region((int)rf.left,(int)rf.top,(int)rf.right,(int)rf.bottom));

                if(touchAction == action){
                    canvas.drawBitmap(bitMapRight,totalRect.right - bitMapRight.getWidth(),startPointF.y,new Paint());
                }
                break;
        }

        canvas.drawPath(path,p);
    }

    private boolean isLongClick = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x;
        float y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                if(touchAction != getAction(x,y)) {
                    invalidate();
                }
                touchAction = getAction(x,y);
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    handler.sendEmptyMessage(ACTION_MESSAGE);
                }
                Log.d("CoustomView3", "onTouchEvent: touchAction: " + touchAction +" x: "+ x+" y: "+y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touchAction = -1;
                invalidate();
                handler.removeMessages(ACTION_MESSAGE);
                break;

        }
        return true;
    }

    private int getAction(float x, float y) {
        if(mRegionTop.contains((int) x, (int) y)){
            return ACTION_TOP;
        }
        if(mRegionBottom.contains((int) x, (int) y)){
            return ACTION_BOTTOM;
        }
        if(mRegionLeft.contains((int) x, (int) y)){
            return ACTION_LEFT;
        }
        if(mRegionRight.contains((int) x, (int) y)){
            return ACTION_RIGHT;
        }
        return -1;
    }

    private float getArcX(float x, float r, float ao) {
        return (float) (x + r * Math.cos(ao * 3.14 / 180));
    }

    private float getArcY(float y, float r, float ao) {
        return (float) (y + r * Math.sin(ao * 3.14 / 180));
    }

    private final int ACTION_MESSAGE = 0xff;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == ACTION_MESSAGE){
                Log.d("CoustomView3", "ACTION_MESSAGE");
                handleAction();
                handler.sendEmptyMessageDelayed(ACTION_MESSAGE,800);
            }
        }
    };

    private void handleAction() {
        if(listener == null) {
            return;
        }
        post(() -> {
            switch (touchAction){
                case ACTION_TOP:
                    listener.onTopAction(CameraControlBar.this);
                    break;
                case ACTION_BOTTOM:
                    listener.onBottomAction(CameraControlBar.this);
                    break;
                case ACTION_LEFT:
                    listener.onLeftAction(CameraControlBar.this);
                    break;
                case ACTION_RIGHT:
                    listener.onRightAction(CameraControlBar.this);
                    break;
            }
        });

    }


    private OnControlBarClickListener listener;

    public void setListener(OnControlBarClickListener listener) {
        this.listener = listener;
    }

    public interface OnControlBarClickListener{
        void onTopAction(View view);
        void onBottomAction(View view);
        void onLeftAction(View view);
        void onRightAction(View view);
    }
}
