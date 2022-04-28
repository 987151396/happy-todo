package com.happy.todo;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testFun1() {
        System.out.println(0x00000001 & 0x00000001);

        System.out.println(0x00000001 & 0x00000002);

        System.out.println(0x00000001 & 0x00000008);
    }



    @Test
    public void testGson() {
        String json = "{\"a\": 2, \"_\":\"6\"}";
        Gson gson = new Gson();
        TestJsonClass jsonClass = gson.fromJson(json, TestJsonClass.class);
        System.out.println(jsonClass.getA() + " //// " + jsonClass.get_());
    }


    @Test
    public void testNumber() {
        for (int i = 0; i < 32; i++) {
            System.out.println((1 << i) - 1);
        }

        System.out.println("max:" + Integer.MAX_VALUE);
        System.out.println("min:" + Integer.MIN_VALUE);
        System.out.println("1<<31:" + (1 << 31) + " >>>>>" + Integer.toBinaryString((1 << 31)));
        System.out.println("-1 = " + Integer.toBinaryString(-1)); //负数的二进制以其补码表示
        System.out.println("-1 >> 2 : " + ((-1) >> 6) + " >>>>>" + Integer.toBinaryString(((-1) >> 6))); //右移符号位不变，左侧补充符号位
        System.out.println("-1 << 31 : " + ((-1) << 31) + " >>>>>" + Integer.toBinaryString(((-1) << 31))); //左移符号位依然不变
    }



    private String formatNumber(long size, double limit) {
        final String[] units = {"KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB", "BB", "NB", "DB", "CB"};
        final DecimalFormat numberFormat = new DecimalFormat("###.###");

        double n = size;
        int i = -1;
        while (n > limit) {
            n /= 1024;
            ++i;
        }

        if (i < 0) {
            return "0 bytes";
        }
        return numberFormat.format(n) + units[i];
    }

    @Test
    public void testBytes() {
        long size = 1000 * 1024;

        String text1 = formatNumber(size, 999.5);
        String text2 = formatNumber(size, 1023);
        System.out.println(text1 + " ==> " + text2);

    }


    @Test
    public void testRx() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        Flowable.just(list)
                .map(new Function<List<Integer>, Object>() {
                    @Override
                    public Object apply(List<Integer> integers) throws Exception {
                        return 2;
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println(o + "");
                    }
                });
    }


    @Test
    public void testSortSet() {
        ArrayList<Integer> list1 = new ArrayList<>();

        list1.add(1);
        list1.add(1);
        list1.add(2);

        List<Integer> deduped = list1.stream().distinct().collect(Collectors.toList());

        for (Integer i : deduped) {
            System.out.println(i);
        }

    }


}