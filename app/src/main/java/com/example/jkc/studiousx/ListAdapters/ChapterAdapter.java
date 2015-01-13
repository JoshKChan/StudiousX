package com.example.jkc.studiousx.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jkc.studiousx.R;
import com.example.jkc.studiousx.StudiousCore.Chapter;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Joshua on 10/01/2015.
 */
public class ChapterAdapter extends ArrayAdapter<Chapter>{

    private ArrayList<Chapter> data;

    public ChapterAdapter(Context context, ArrayList<Chapter> data){
        super(context, R.layout.rowlayout_courses,data);
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.rowlayout_chapter,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.rowlayout_chapter_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.rowlayout_chapter_description);;
            viewHolder.number = (TextView) convertView.findViewById(R.id.rowlayout_chapter_number);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(!data.isEmpty()) {
            Chapter chapter = data.get(position);
            viewHolder.description.setText(chapter.getDescription());
            viewHolder.title.setText(chapter.getName());
//            viewHolder.number.setText(chapter.getNumber());
        }
        Log.w("ChapterAdapter","Rendered view...");
        return convertView;
    }

    private static class ViewHolder{
        public TextView title;
        public TextView description;
        public TextView number;
    }

}
