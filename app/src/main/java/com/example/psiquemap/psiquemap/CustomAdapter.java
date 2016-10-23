package com.example.psiquemap.psiquemap;

/**
 * Created by yuki on 18/10/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int emojis[];
    String[] nomeSentimentos;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] emojis, String[] nomeSentimentos) {
        this.context = applicationContext;
        this.emojis = emojis;
        this.nomeSentimentos = nomeSentimentos;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return emojis.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(emojis[i]);
        names.setText(nomeSentimentos[i]);
        return view;
    }
}

