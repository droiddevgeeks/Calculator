package com.example.kishanmaurya.calc.main;

import android.view.View;

/**
 * Created by kishanmaurya on 7/12/17.
 */

public interface MainPresenter
{

    void onEvaluateClicked(View view);

    void setData(String value);

    String getData();

    void onResume();

    void onDestroy();
}