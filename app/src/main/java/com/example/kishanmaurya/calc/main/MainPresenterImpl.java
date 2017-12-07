package com.example.kishanmaurya.calc.main;

import android.app.Activity;
import android.view.View;

import com.example.kishanmaurya.calc.R;
import com.example.kishanmaurya.calc.calculator.IResultListener;
import com.example.kishanmaurya.calc.calculator.PostFixCalculator;
import com.example.kishanmaurya.calc.calculator.PostFixConverter;
import com.example.kishanmaurya.calc.keyboard.IUserInputListener;
import com.example.kishanmaurya.calc.keyboard.KeyboardView;

import java.math.BigDecimal;

/**
 * Created by kishanmaurya on 7/12/17.
 */

public class MainPresenterImpl implements MainPresenter, IUserInputListener, IResultListener
{

    private MainView mainView;
    private StringBuilder equation;
    private KeyboardView keyboardView;
    private boolean alreadyHaveResult = false;

    public MainPresenterImpl(MainView mainView)
    {
        this.mainView = mainView;
        equation = new StringBuilder();
    }

    @Override
    public void onResume()
    {
        keyboardView = (KeyboardView) ((Activity) mainView).findViewById(R.id.keyboard);
        keyboardView.setOnKeyPressedListener(this);
    }

    /**
     * this method is used to save value while rotating screen
     *
     * @param value
     */
    @Override
    public void setData(String value)
    {
        equation.append(value);
    }

    /**
     * this method is used to get stored value to display again on screen after rotation
     *
     * @return
     */
    @Override
    public String getData()
    {
        return equation.toString();
    }

    /**
     * When user click on keyboard, Controll comes here with code and press key value
     *
     * @param keyCode
     * @param value
     */
    @Override
    public void onUserInput(int keyCode, String value)
    {

        /**
         * alreadyHaveResult var is used to decide whether we already have calculation data or not.
         * If we already have data then if user press operator then same result will be used otherwise
         * we will start new calculation
         */
        if (!alreadyHaveResult)
        {
            if (value.equalsIgnoreCase("."))
            {
                if (equation.length() == 0 || PostFixConverter.isOperator(equation.charAt(equation.length() - 1)))
                    equation.append("0");
            }

            if (equation.length() == 0 && !checkFirstInputValid(value))
            {
                //Toast.makeText(this, getString(R.string.invalid_op), Toast.LENGTH_SHORT).show();
                // do nothing
            }
            else
            {

                equation.append(value);
                mainView.setValue(equation.toString());

            }
        }
        else
        {
            alreadyHaveResult = false;
            if (PostFixConverter.isOperator(value.charAt(value.length() - 1)))
            {

                if (value.equalsIgnoreCase("."))
                {
                    if (equation.length() == 0 || PostFixConverter.isOperator(equation.charAt(equation.length() - 1)))
                        equation.append("0");
                }

                if (equation.length() == 0 && !checkFirstInputValid(value))
                {
                    //Toast.makeText(this, getString(R.string.invalid_op), Toast.LENGTH_SHORT).show();
                    // do nothing
                }
                else
                {

                    equation.append(value);
                    mainView.setValue(equation.toString());

                }
            }
            else
            {
                /**
                 * This logic is used for new calculation if user has pressed operand key after previous calculation
                 */
                equation.setLength(0);
                equation.append(value);
                mainView.setValue(equation.toString());
            }
        }
    }


    /**
     * This method is used to Clear last enter value
     */
    @Override
    public void onClearLast()
    {
        if (equation.length() > 1)
        {
            equation.deleteCharAt(equation.length() - 1);
            mainView.setValue(equation.toString());
        }
        else if (equation.length() == 1) // if only 1 digit is there then set 0 on screen
        {
            equation.deleteCharAt(equation.length() - 1);
            mainView.setValue("0");
        }
    }

    /**
     * this method is used to clear entire data from keyboard
     */
    @Override
    public void onClear()
    {
        equation.setLength(0);
        mainView.setValue("0");
    }

    /**
     * This method is used to call evaluate string
     *
     * @param view this view is corresponding click on "=" button
     */
    @Override
    public void onEvaluateClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.txtEqual:
                if (equation.length() == 0)
                {
                    // do nothing
                }
                else
                {

                    checkAndEvaluate();
                }
                break;
        }
    }

    private void checkAndEvaluate()
    {
        PostFixConverter postFixConverter = new PostFixConverter();
        /**
         *  If input expression is valid then we will convert it to postfix
         */
        if (postFixConverter.checkStringValidity(equation.toString()))
        {
            postFixConverter.modifyInfixString(equation.toString());
            PostFixCalculator calc = new PostFixCalculator(postFixConverter.getPostfixAsList(), this);
            calc.result();
        }
        else
        {
            mainView.showMessage("Invalid Operation");
        }

    }


    /**
     * If first input is operator then do nothing except for "-" as we have unary minus operation
     *
     * @param value
     * @return
     */
    private boolean checkFirstInputValid(String value)
    {
        switch (value)
        {
            case "%":
            case "/":
            case "^":
            case "*":
            case "+":
                return false;

        }
        return true;
    }


    @Override
    public void onResultUpdate(BigDecimal result)
    {
        alreadyHaveResult = true;
        mainView.setValue(String.valueOf(result));
        equation.setLength(0);
        equation.append(result);
    }

    @Override
    public void onException(String type)
    {
        mainView.showMessage(type);
    }

    @Override
    public void onDestroy()
    {
        mainView = null;
        keyboardView = null;
        equation = null;
    }

}
