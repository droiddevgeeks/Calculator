package com.example.kishanmaurya.calc.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kishanmaurya.calc.R;

/**
 * Created by kishanmaurya on 5/12/17.
 */

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener
{

    private TextView userInput;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenterImpl(this);
        initView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putString("RESULT", presenter.getData());
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        String value = savedInstanceState.getString("RESULT");
        if (TextUtils.isEmpty(value))
        {
            userInput.setText("0");
        }
        else
        {
            presenter.setData(value);
            userInput.setText(value);
        }
    }


    private void initView()
    {
        userInput = (TextView) findViewById(R.id.txtInput);
        userInput.setText("0");
        TextView evaluate = (TextView) findViewById(R.id.txtEqual);
        evaluate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        presenter.onEvaluateClicked(view);
    }

    @Override
    protected void onDestroy()
    {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setValue(String value)
    {
        userInput.setText(value);
    }

    @Override
    public void showMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
