package com.happy.todo.lib_common.utils;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.happy.todo.lib_common.R;

import java.util.ArrayList;

/**
 * @功能描述：富文本工具类
 * @创建日期： 2018/08/10
 * @作者： dengkewu
 */

public class SpannableUtils {

    /**
     * 设置字体颜色，默认为黑色
     *
     * @param content
     * @param start   起始坐标
     * @param end     结束坐标
     * @return
     */
    public static SpannableString setTextColor(String content, int start, int end) {
        if (start < 0 || start > end) {
            try {
                throw new Exception("非法参数==>start=" + start);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (end < 0 || end > content.length()) {
            try {
                throw new Exception("非法参数==>end=" + end);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SpannableString spannableString = new SpannableString(content);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.8f);
        spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ResourceUtils.getColor(R.color.black_484848));
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 设置字体颜色，默认为黑色
     *
     * @param content
     * @param start   起始坐标
     * @param end     结束坐标
     * @return
     */
    public static SpannableString setTextColor(String content, int start, int end, int color) {
        if (start < 0 || start > end) {
            try {
                throw new Exception("非法参数==>start=" + start);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (end < 0 || end > content.length()) {
            try {
                throw new Exception("非法参数==>end=" + end);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SpannableString spannableString = new SpannableString(content);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.8f);
        spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    /**
     * 字符串中字体变色，默认为绿色
     *
     * @param str
     * @return
     */
    public static SpannableStringBuilder setNumColor(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9') {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(ResourceUtils.getColor(R.color.theme_color));
                style.setSpan(colorSpan, i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }


    /**
     * 字符串中数字变粗
     *
     * @param str
     * @return
     */
    public static SpannableStringBuilder setNumBold(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9') {
                ClickableSpan clickSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(ResourceUtils.getColor(R.color.black_484848));
                        ds.setUnderlineText(false);//设置下划线
                        ds.setTextSize(SizeUtil.sp2px(16));
                        @SuppressLint("WrongConstant")
                        Typeface typeface = Typeface.create(ResourceUtils.getString(R.string.font_fontFamily_medium), R.style.Font_Medium);
                        ds.setTypeface(typeface);
                    }
                };
                style.setSpan(clickSpan, i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }

    /**
     * 字符串中字体变色
     *
     * @param str
     * @param color
     * @return
     */
    public static SpannableStringBuilder setNumColor(String str, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9') {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                style.setSpan(colorSpan, i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }

    /**
     * 设置价格变色
     *
     * @param str           显示字符串
     * @param currency_type 需要设置样式的货币
     * @param price         需要设置样式的价格
     * @return
     */
    public static SpannableString setPriceColor(String str, String currency_type, String price) {
        SpannableString spannableString = new SpannableString(str);
        if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(currency_type)) {
            if (!str.contains(price)) {
                return new SpannableString(str);
            }
            //设置价格变色并且变大
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(R.color.red_E05857));
                    ds.setUnderlineText(false);//设置下划线
                    ds.setTextSize(SizeUtil.sp2px(22));
                    ds.setFakeBoldText(true);

                }
            };
            spannableString.setSpan(clickSpan, str.indexOf(price), str.indexOf(price) + price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置价格变色并且变大
            ClickableSpan clickSpan1 = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(R.color.red_E05857));
                    ds.setTextSize(SizeUtil.sp2px(12));
                    ds.setUnderlineText(false);//设置下划线
                }
            };
            int start = 0;
            if (str.indexOf(currency_type) != -1) {
                start = str.indexOf(currency_type);
            }
            spannableString.setSpan(clickSpan1, start, start + currency_type.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannableString;
    }

    /**
     * 字符串中字体变色
     *
     * @return
     */
    public static SpannableString setPriceColor_Line(String str, String currency_type, String price, String oldPrice) {
        String newPrice = currency_type + " " + price;
        int index = str.indexOf("=");
        str = str.replace("="," ");
        SpannableString spannableString = new SpannableString(str);
        if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(currency_type)) {
//            String str1 = str.toLowerCase();
            if (!str.contains(newPrice)) {
                return new SpannableString(str);
            }
            //设置价格变色
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(R.color.red_E05857));
                    ds.setUnderlineText(false);//设置下划线
                }
            };
            /*int start2 = 0;
            if (str.indexOf(newPrice) != -1) {
                start2 = str.indexOf(newPrice);
            }*/

            int start2 = index;
            spannableString.setSpan(clickSpan, start2, start2 + newPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置价格变大
            ClickableSpan clickSpan1 = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setTextSize(SizeUtil.sp2px(22));
                    ds.setColor(ResourceUtils.getColor(R.color.red_E05857));
                    ds.setFakeBoldText(true);
                    ds.setUnderlineText(false);//设置下划线
                }
            };
            int start = 0;
            if (str.indexOf(price) != -1) {
                start = str.indexOf(price,start2);
            }
            spannableString.setSpan(clickSpan1, start, start + price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置价格有删除线
            ClickableSpan clickSpan2 = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);//设置下划线
                    ds.setStrikeThruText(true);
                    ds.setColor(ResourceUtils.getColor(R.color.gray_949494));
                }
            };
            int start1 = 0;
            if (str.indexOf(oldPrice) != -1) {
                start1 = str.indexOf(oldPrice);
            }
            spannableString.setSpan(clickSpan2, start1, start1 + oldPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannableString;
    }

    /**
     * 设置价格变粗
     *
     * @param str 显示字符串
     * @return
     */
    public static SpannableString setPriceMedium(String str, String currency_type, String price) {

        SpannableString spannableString = new SpannableString(str);
        if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(currency_type)) {
            if (!str.contains(price)) {
                return new SpannableString(str);
            }
            //设置价格变色并且变大
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(R.color.black_484848));
                    ds.setUnderlineText(false);//设置下划线
                    ds.setTextSize(SizeUtil.sp2px(18));
                    ds.setFakeBoldText(true);

                }
            };
            spannableString.setSpan(clickSpan, str.indexOf(price), str.indexOf(price) + price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ClickableSpan clickSpan1 = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(R.color.black_484848));
                    ds.setTextSize(SizeUtil.sp2px(14));
                    ds.setUnderlineText(false);//设置下划线
                }
            };
            int start = 0;
            if (str.indexOf(currency_type) != -1) {
                start = str.indexOf(currency_type);
            }
            spannableString.setSpan(clickSpan1, start, start + currency_type.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannableString;
    }

    /**
     * 设置文字变粗
     *
     * @param str 显示字符串
     * @return
     */
    public static SpannableString setTextMedium(String str, String beStr) {

        SpannableString spannableString = new SpannableString(str);

            //设置文字变色并且变大
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(R.color.black_2D2D2D));
                    ds.setUnderlineText(false);//设置下划线
                    ds.setTextSize(SizeUtil.sp2px(16));
                    //ds.setFakeBoldText(true);
                    @SuppressLint("WrongConstant")
                    Typeface typeface = Typeface.create(ResourceUtils.getString(R.string.font_fontFamily_medium), R.style.Font_Medium);
                    ds.setTypeface(typeface);
                }
            };
            spannableString.setSpan(clickSpan, str.indexOf(beStr), str.indexOf(beStr) + beStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        return spannableString;
    }

    /**
     * 在文本后面追加星级
     *
     * @param star
     * @return
     */
    public static SpannableStringBuilder setStar(String charSequence, int star) {
        String text = charSequence + "  ";
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        for (int i = 0; i < star; i++) {
            //用于占位，后面替换成图片
            spannable.append("[x]");
            Drawable drawable = ResourceUtils.getDrawable(R.mipmap.hotel_star);
            drawable.setBounds(4, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            //第一个参数drawable 第二个参数对齐方式
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannable.setSpan(imageSpan, spannable.length() - 3 * (i + 1), spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static SpannableStringBuilder setStar_trim(String charSequence, int star) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(charSequence);
        for (int i = 0; i < star; i++) {
            //用于占位，后面替换成图片
            spannable.append("[x]");
            Drawable drawable = ResourceUtils.getDrawable(R.mipmap.hotel_star);
            drawable.setBounds(4, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            //第一个参数drawable 第二个参数对齐方式
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannable.setSpan(imageSpan, spannable.length() - 3 * (i + 1), spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    /**
     *文本后追加圆形星级
     *
     * @param star
     * @return
     */
    public static SpannableStringBuilder setCircleStar(String charSequence,float star) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(charSequence);
        int num = 0;
        for (int i = 0; i < (int) star; i++) {
            //用于占位，后面替换成图片
            spannable.append("[*]");
            Drawable drawable = ResourceUtils.getDrawable(R.mipmap.hotel_circle_star_all);
            drawable.setBounds(2, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            //第一个参数drawable 第二个参数对齐方式
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannable.setSpan(imageSpan, spannable.length() - 3 * (i + 1), spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            num++;
        }

        //如果有半星
        if (star % 1 != 0) {
            spannable.append("[*]");
            Drawable drawable = ResourceUtils.getDrawable(R.mipmap.hotel_circle_star_half);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            //第一个参数drawable 第二个参数对齐方式
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannable.setSpan(imageSpan, spannable.length() - 3, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            num++;
        }
        /*for(int a = 0; a < 5-num; a++) {
            spannable.append("[*]");
            Drawable drawable = ResourceUtils.getDrawable(R.mipmap.hotel_circle_star_null);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            //第一个参数drawable 第二个参数对齐方式
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannable.setSpan(imageSpan, spannable.length() - 3, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }*/
        return spannable;
    }

    public static SpannableString setPriceColor_Line2(String str, String currency_type, String price, String oldPrice) {
        SpannableString spannableString = new SpannableString(str);
        if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(currency_type)) {
            //设置价格变色
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(R.color.red_E05857));
                    ds.setUnderlineText(false);//设置下划线
                }
            };

            spannableString.setSpan(clickSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置价格变大
            ClickableSpan clickSpan1 = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setTextSize(SizeUtil.sp2px(22));
                    ds.setColor(ResourceUtils.getColor(R.color.red_E05857));
                    ds.setFakeBoldText(true);
                    ds.setUnderlineText(false);//设置下划线
                }
            };
            int start = 0;
            if (str.indexOf(price) != -1) {
                start = str.indexOf(price);
            }
            spannableString.setSpan(clickSpan1, start, start + price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            //设置价格有删除线
            ClickableSpan clickSpan2 = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);//设置下划线
                    ds.setStrikeThruText(true);
                    ds.setColor(ResourceUtils.getColor(R.color.gray_767676));
                }
            };
            int start1 = 0;
            if (str.indexOf(oldPrice) != -1) {
                start1 = str.indexOf(oldPrice);
            }
            spannableString.setSpan(clickSpan2, start1, start1 + oldPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannableString;
    }

    /**
     * 设置文字变粗变大
     *
     * @param str 显示字符串
     * @return
     */
    public static SpannableString setTextMediumAndBig(String str, String beStr,int color,int textSize) {
        SpannableString spannableString = new SpannableString(str);
        //设置文字变色并且变大
        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) { }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ResourceUtils.getColor(color));
                ds.setUnderlineText(false);//设置下划线
                ds.setTextSize(SizeUtil.sp2px(textSize));
                ds.setFakeBoldText(true);

            }
        };
        spannableString.setSpan(clickSpan, str.indexOf(beStr), str.indexOf(beStr) + beStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 设置文字变粗变大
     *
     * @param str 显示字符串
     * @return
     */
    public static SpannableString setTitelListMediumAndBig(String str, ArrayList<String> arrayList, int color, int textSize) {
        String beStr;
        SpannableString spannableString = new SpannableString(str);
        for (int a = 0; a < arrayList.size(); a++) {
            beStr = arrayList.get(a);
            //设置文字变色并且变大
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) { }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(color));
                    ds.setUnderlineText(false);//设置下划线
                    ds.setTextSize(SizeUtil.sp2px(textSize));
                    ds.setFakeBoldText(true);
                }
            };
            spannableString.setSpan(clickSpan, str.indexOf(beStr), str.indexOf(beStr) + beStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 设置文字变粗变大
     *
     * @param str 显示字符串
     * @return
     */
    public static SpannableString setTitelListFontMediumAndBig(String str, ArrayList<String> arrayList, int color, int textSize) {
        String beStr;

        SpannableString spannableString = new SpannableString(str);

        for (int a = 0; a < arrayList.size(); a++) {
            beStr = arrayList.get(a);


            //设置文字变色并且变大
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ResourceUtils.getColor(color));
                    ds.setUnderlineText(false);//设置下划线
                    ds.setTextSize(SizeUtil.sp2px(textSize));
                    //ds.setFakeBoldText(true);
                    @SuppressLint("WrongConstant")
                    Typeface typeface = Typeface.create(ResourceUtils.getString(R.string.font_fontFamily_medium), R.style.Font_Medium);
                    ds.setTypeface(typeface);
                }
            };

            spannableString.setSpan(clickSpan, str.indexOf(beStr), str.indexOf(beStr) + beStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        return spannableString;
    }

}
