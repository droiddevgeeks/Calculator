package com.example.kishanmaurya.calc.keyboard;

/**
 * Created by kishanmaurya on 5/12/17.
 */

public interface IUserInputListener
{
    void onUserInput(int keyCode, String value);
    void onClearLast();
    void onClear();
}
