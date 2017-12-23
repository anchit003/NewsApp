package com.example.anchitchawla.news;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anchit Chawla on 14-12-2017.
 */

public class CustomAdapter extends ArrayAdapter<News> {
    public CustomAdapter(Activity context, ArrayList<News> words) {
        super(context, 0, words);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.news_list, parent, false);
        }
        News curr=getItem(position);
        TextView textView=(TextView)view.findViewById(R.id.section);
        textView.setText(curr.getSection());
        TextView textView1=(TextView)view.findViewById(R.id.aa);
        textView1.setText(curr.getTitle());
        TextView textView2=(TextView)view.findViewById(R.id.type);
        textView2.setText(curr.getType());
        return view;
    }
}