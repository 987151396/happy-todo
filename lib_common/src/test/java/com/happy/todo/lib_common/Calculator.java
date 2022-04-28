package com.happy.todo.lib_common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Jaminchanks on 2018/3/15.
 */

public class Calculator {

    public static String[] OPERATORS = {"+", "-", "×", "÷"};
    public static String OPERATORS_REG = "\\+|-|×|÷";

    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String MUL = "×";
    public static final String DIV = "÷";

    public static BigDecimal calculate(String exp) {
        List<String> infixList = new ArrayList<>();
        String[] values = exp.split(OPERATORS_REG);

        int operatorIndex = -1;

        for (int i = 0; i < values.length; i++) {
            infixList.add(values[i]);
            if (i != values.length - 1) {
                operatorIndex += (values[i].length() + 1);
                String operator = exp.toCharArray()[operatorIndex] + "";
                infixList.add(operator);
            }
        }

        System.out.print("中缀：");
        for (String val : infixList) {
            System.out.print(val + " ");
        }
        System.out.println();


        return calculate(infix2Postfix(infixList));
    }



    /**
     * 由后缀表达式计算表达式的结果
     * @param postfix 后缀表达式
     * @return 返回表达式的结果
     */
    private static BigDecimal calculate(List<String> postfix) {

        Stack<BigDecimal> stack = new Stack<>();
        int len = postfix.size();
        String temp;

        for(int i = 0; i < len; i++) {
            temp = postfix.get(i);
            if(!isOperator(temp)) {     // 若为操作数
                stack.push(new BigDecimal(temp));
                System.out.println("push " + temp);

            } else {
                BigDecimal val = new BigDecimal(0);
                BigDecimal num1 = stack.pop();
                System.out.println("pop " + num1);
                BigDecimal num2 = stack.pop();
                System.out.println("pop " + num2);
                switch (temp) {
                    case ADD:
                        val = num2.add(num1);
                        System.out.println(num2 + " + " + num1);
                        break;

                    case SUB:
                        val = num2.subtract(num1);  // 注意顺序
                        System.out.println(num2 + " - " + num1);
                        break;

                    case MUL:
                        val = num2.multiply(num1);
                        System.out.println(num2 + " * " + num1);
                        break;

                    case DIV:
                        val = num2.divide(num1, 2, BigDecimal.ROUND_UP);
                        System.out.println(num2 + " /" + num1);
                        break;

                    default:
                        break;
                }
                stack.push(val);
                System.out.println("push " + val);

            }
        }

        return stack.pop();
    }


    /**
     * 中缀表达式转换为后缀表达式
     * @param infix 中缀表达式
     * @return 返回后缀表达式
     */
    private static List<String> infix2Postfix(List<String> infix) {
        List<String> postfix = new ArrayList<>();

        Stack<String> stack = new Stack<>();
        int len = infix.size();
        String temp;

        for(int i = 0; i < len; i++) {
            temp = infix.get(i);

            if(temp.equals(" "))
                continue;

            if (!isOperator(temp)) {     // 若为操作数
                postfix.add(temp);
            } else {
                // 从栈中弹出所有优先级比当前运算符高的运算符, 并放进队列中
                while (!stack.isEmpty()
                        && compareOperatorPriority(stack.peek(), temp)) {
                    postfix.add(stack.pop());
                }
                stack.push(temp);   // 操作符进栈
            }
        }

        // 把栈中的所有元素弹出, 放进队列中
        while(!stack.isEmpty()) {
            postfix.add(stack.pop());
        }


        System.out.print("后缀：");
        for (String val : postfix) {
            System.out.print(val+ " ");
        }
        System.out.println();

        return postfix;
    }

    private static boolean compareOperatorPriority(String peek, String temp) {
        return getPriority(peek) <= getPriority(temp);
    }


    private static int getPriority(String operator) {
        if (ADD.equals(operator) || SUB.equals(operator)) {
            return 2;
        }
        if (MUL.equals(operator) || DIV.equals(operator)) {
            return 1;
        }
        return 0;
    }



    public static boolean isOperator(String temp) {
        return ADD.equals(temp) || SUB.equals(temp) || MUL.equals(temp) || DIV.equals(temp);
    }


}
