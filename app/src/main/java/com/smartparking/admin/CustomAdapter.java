package com.smartparking.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 3/19/2018.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    String List[];
    int flags[];
    LayoutInflater inflter;
    public CustomAdapter(Context applicationContext, String[] List, int[] flags) {
        this.context = context;
        this.List = List;
        this.flags = flags;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return List.length;
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
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView country = (TextView)           view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        country.setText(List[i]);
        icon.setImageResource(flags[i]);
        return view;
    }
}
