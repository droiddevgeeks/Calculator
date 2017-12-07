package com.example.kishanmaurya.calc.calculator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by kishanmaurya on 6/12/17.
 */

public class PostFixCalculator
{
    private List<String> expression = new ArrayList<String>();
    private Deque<Double> stack = new ArrayDeque<Double>();
    private IResultListener listener;

    public PostFixCalculator(List<String> postfix, IResultListener listener)
    {
        expression = postfix;
        this.listener = listener;
    }


    public void result()
    {
        for (int i = 0; i != expression.size(); ++i)
        {
            if (Character.isDigit(expression.get(i).charAt(0)))
            {
                stack.addLast(Double.parseDouble(expression.get(i)));
            }
            else
            {
                double tempResult = 0;
                double temp;

                switch (expression.get(i))
                {
                    case "+":
                        temp = stack.removeLast();
                        tempResult = stack.removeLast() + temp;
                        break;

                    case "-":
                        temp = stack.removeLast();
                        tempResult = stack.removeLast() - temp;
                        break;

                    case "*":
                        temp = stack.removeLast();
                        tempResult = stack.removeLast() * temp;
                        break;

                    case "/":
                        try
                        {
                            temp = stack.removeLast();
                            if (temp == 0.0)
                                throw new ArithmeticException("Divide by 0");
                            tempResult = stack.removeLast() / temp;
                        }
                        catch (ArithmeticException e)
                        {
                            listener.onException("Divide by 0");
                            return;
                        }

                        break;
                    case "^":
                        temp = stack.removeLast();
                        tempResult = Math.pow(stack.removeLast(), temp);
                        break;
                    case "%":
                        try
                        {
                            temp = stack.removeLast();
                            if (temp == 0.0)
                                throw new ArithmeticException("Modulo by 0");
                            tempResult = stack.removeLast() % temp;
                        }
                        catch (ArithmeticException e)
                        {
                            listener.onException("Modulo by 0");
                            return;
                        }


                }
                stack.addLast(tempResult);
            }
        }
        listener.onResultUpdate(new BigDecimal(stack.removeLast()));
    }

}