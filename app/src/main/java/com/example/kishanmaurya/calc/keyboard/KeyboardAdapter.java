package com.example.kishanmaurya.calc.keyboard;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kishanmaurya.calc.R;

/**
 * Created by kishanmaurya on 5/12/17.
 */

public class KeyboardAdapter extends BaseAdapter
{


    private Context _context;
    private String[] _keys;
    private LayoutInflater _inflater;
    private AdapterView.OnItemClickListener _itemClickListener;

    public KeyboardAdapter(Context context, AdapterView.OnItemClickListener listener)
    {
        _context = context;
        _itemClickListener = listener;
        _inflater = LayoutInflater.from(context);
        _keys = _context.getResources().getStringArray(R.array.keys);
    }

    @Override
    public int getCount()
    {
        return _keys.length;
    }

    @Override
    public String getItem(int position)
    {
        return _keys[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = _inflater.inflate(R.layout.keyboard_view, parent, false);
            holder = new ViewHolder();
            holder.key = (TextView) convertView.findViewById(R.id.key_name);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        String keyValue = getItem(position);
        holder.key.setText(keyValue);
        setCalculatorBackground(convertView, keyValue);
        convertView.setOnClickListener(new ItemClickListener(position));
        return convertView;

    }


    private static class ViewHolder
    {
        private TextView key;
    }

    private void setCalculatorBackground(View view, String key)
    {
        switch (key)
        {
            case "CL":
            case "CE":
                view.setBackground(ContextCompat.getDrawable(_context, R.drawable.cl_button_bg));
                break;
            case "%":
            case "/":
            case "^":
            case "*":
            case "-":
            case "+":
                view.setBackground(ContextCompat.getDrawable(_context, R.drawable.operator_button_bg));
                break;

        }
    }

    private class ItemClickListener implements View.OnClickListener
    {
        private int _position;

        ItemClickListener(int position)
        {
            _position = position;
        }

        @Override
        public void onClick(View v)
        {
            TextView textView = (TextView) v.findViewById(R.id.key_name);
            _itemClickListener.onItemClick(null, textView, _position, 0);
        }
    }
}

