package com.happy.todo.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.happy.todo.R;

/**
 * Author by Ouyangle, Date on 2021/1/22.
 * PS: Not easy to write code, please indicate.
 */
public class MatrixCoustomView extends View {
    private Bitmap bitmap;
    private Paint paint;
    private Rect src;
    private Rect src2;
    private int width;
    private int height;
    private Matrix m;

    public MatrixCoustomView(Context context) {
        super(context);
        init(context);
    }

    public MatrixCoustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MatrixCoustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hotel_img);

        // 取 drawable 的长宽
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        src = new Rect(0,0,width,height);
        src2 = new Rect(0,0,width,height);

        m = new Matrix();
        m.setTranslate(100, 100);
        m.preSkew(0.5f,0.5f);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("onDraw","width : " + width + "--- height : " + height);


        canvas.setMatrix(m);
        canvas.drawBitmap(bitmap,src,src2,paint);
        //canvas.drawBitmap(bitmap,m,paint);
    }
}
