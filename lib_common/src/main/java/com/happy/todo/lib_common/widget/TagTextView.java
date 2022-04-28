/*
package com.ifreegroup.app.ebbly.lib_common.widget;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class TagTextView  extends TextView {

    private StringBuffer content_buffer;

    private TextView tv_tag;

    private View view;//标签布局的最外层布局

    private Context mContext;

//必须重写所有的构造器，否则可能会出现无法inflate布局的错误！

    public TagTextView(Context context) {

        super(context);

        mContext = context;

    }



    public TagTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        mContext = context;

    }



    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        mContext = context;

    }







    public void setContentAndTag(String content, List<String> tags){

        content_buffer = new StringBuffer(content);

        for (String item : tags) {//将每个tag的内容添加到content后边，之后将用drawable替代这些tag所占的位置

            content_buffer.append(item);

        }



        SpannableString spannableString = new SpannableString(content_buffer);

        int replaceLength = content.length();

        for (int i = 0;i<tags.size();i++){

            String item = tags.get(i);

            View view = LayoutInflater.from(mContext).inflate(R.layout.tag, null);//R.layout.tag是每个标签的布局

            tv_tag = ((TextView)view.findViewById(R.id.tv_tag));

            tv_tag.setText(item);

            if (item.equals("HOT")){

                tv_tag.setBackgroundResource(R.drawable.red_bg);

            }else if (item.equals("NEW")){

                tv_tag.setBackgroundResource(R.drawable.orange_bg);

            }else if (item.equals("软文版")){

                tv_tag.setBackgroundResource(R.drawable.yellow_bg);

            }

            Bitmap bitmap = convertViewToBitmap(view);

            Drawable d = new BitmapDrawable(bitmap);

            d.setBounds(0,0,tv_tag.getWidth(), tv_tag.getHeight());//缺少这句的话，不会报错，但是图片不回显示

            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);//图片将对齐底部边线

            spannableString.setSpan(span,replaceLength, replaceLength+item.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            replaceLength += item.length();

        }

        setText(spannableString);

        setGravity(Gravity.CENTER_VERTICAL);

    }



    private static Bitmap convertViewToBitmap(View view){

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();



        return bitmap;

    }



}
————————————————
        版权声明：本文为CSDN博主「RadishSu」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/shw372029857/article/details/52471624
*/
