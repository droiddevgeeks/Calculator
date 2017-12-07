package com.example.kishanmaurya.calc.calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by kishanmaurya on 6/12/17.
 */

public class PostFixConverter
{
    private Deque<Character> stack = new ArrayDeque<Character>();
    private List<String> postfix = new ArrayList<String>();

    /**
     * This method check validity of equation to evaluate.
     * If last input is operator then invalid
     * if there are successive operator then invalid
     *
     * @param input
     * @return
     */
    public boolean checkStringValidity(String input)
    {
        if (isOperator(input.charAt(input.length() - 1)))
        {
            return false;
        }
        for (int i = 0; i < input.length() - 1; i++)
        {

            if (isOperator(input.charAt(i)) && isOperator(input.charAt(i + 1)))
            {
                return false;
            }

        }
        return true;
    }

    public static boolean isOperator(char a)
    {
        switch (a)
        {
            case '%':
            case '/':
            case '^':
            case '*':
            case '-':
            case '+':
                return true;
        }
        return false;
    }

    /**
     * this is to handle unary minus operator.
     * -5-6 ===>>  (0-5)+(0-6) like this
     * If I get "-" at first place then just replace it by (0-5)
     * If I get "-" at other place then just replace it by +(0-5)
     * @param expression
     */
    public void modifyInfixString(String expression)
    {
        StringBuilder sb = new StringBuilder(expression);

        for (int i = 0; i < sb.length() - 1; i++)
        {
            if (sb.charAt(i) == '-' && i == 0)
            {
                String temp = "(0-" + sb.charAt(i + 1) + ")";
                sb.delete(i, i + 2);
                String rem = sb.substring(i);
                sb.setLength(0);
                sb.append(temp);
                sb.append(rem);
                i = i + temp.length() - 1;

            }
            else if (sb.charAt(i) == '-')
            {

                String temp = "+(0-" + sb.charAt(i + 1) + ")";
                sb.delete(i, i + 2);
                String rem = sb.substring(i);
                String prev = sb.substring(0, i);
                sb.setLength(0);
                sb.append(prev);
                sb.append(temp);
                sb.append(rem);
                i = i + temp.length() - 1;

            }
        }

        convertExpression(sb.toString());
    }


    /**
     * This method is used to convert user infix input to postfix.
     * After converting postfix, We will evaluate this postfix.
     * @param infix
     */
    private void convertExpression(String infix)
    {
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i != infix.length(); ++i)
        {
            if (Character.isDigit(infix.charAt(i)))
            {
                temp.append(infix.charAt(i));

                while ((i + 1) != infix.length() && (Character.isDigit(infix.charAt(i + 1)) || infix.charAt(i + 1) == '.'))
                {
                    temp.append(infix.charAt(++i));
                }

                postfix.add(temp.toString());
                temp.delete(0, temp.length());
            }
            else
                inputToStack(infix.charAt(i));
        }
        clearStack();
    }


    private void inputToStack(char input)
    {
        if (stack.isEmpty() || input == '(')
            stack.addLast(input);
        else
        {
            if (input == ')')
            {
                while (!stack.getLast().equals('('))
                {
                    postfix.add(stack.removeLast().toString());
                }
                stack.removeLast();
            }
            else
            {
                if (stack.getLast().equals('('))
                    stack.addLast(input);
                else
                {
                    while (!stack.isEmpty() && !stack.getLast().equals('(') && getPrecedence(input) <= getPrecedence(stack.getLast()))
                    {
                        postfix.add(stack.removeLast().toString());
                    }
                    stack.addLast(input);
                }
            }
        }
    }


    /**
     * "^" has highest priority
     * "* / %" have second highest
     * " + -" have last priority
     * @param op
     * @return
     */
    private int getPrecedence(char op)
    {
        if (op == '+' || op == '-')
            return 1;
        else if (op == '*' || op == '/' || op == '%')
            return 2;
        else if (op == '^')
            return 3;
        else return 0;
    }


    /**
     * To clear stack values
     */
    private void clearStack()
    {
        while (!stack.isEmpty())
        {
            postfix.add(stack.removeLast().toString());
        }
        stack = null;
    }


    public List<String> getPostfixAsList()
    {
        return postfix;
    }
}