package com.example.kishanmaurya.calc.calculator;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kishanmaurya on 6/12/17.
 */

public interface IResultListener
{
    void onResultUpdate(BigDecimal decimal);
    void onException(String type);
}
