package com.example.kishanmaurya.calc.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by kishanmaurya on 5/12/17.
 */

public class KeyboardView extends GridView implements AdapterView.OnItemClickListener
{

    private IUserInputListener _keyClickedListener;

    public KeyboardView(Context context)
    {
        super(context);
        init();
    }

    public KeyboardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        setAdapter(new KeyboardAdapter(this.getContext(), this));
    }

    public void setOnKeyPressedListener(IUserInputListener listener)
    {
        _keyClickedListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView textView  = (TextView)view;
        String keyPressed = (String)textView.getText();

        if (_keyClickedListener != null)
        {
            switch (keyPressed)
            {
                case "CE":
                    _keyClickedListener.onClearLast();
                    break;
                case "CL":
                    _keyClickedListener.onClear();
                    break;
                case "":
                    // do nothing
                    break;
                default:
                    _keyClickedListener.onUserInput(position, keyPressed);
                    break;
            }
        }
    }


}
