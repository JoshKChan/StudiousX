package com.example.jkc.studiousx.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jkc.studiousx.R;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Joshua on 31/12/2014.
 */
public class CourseAdapter extends ArrayAdapter<File> {

    private ArrayList<File> data;

    public CourseAdapter(Context context, ArrayList<File> data){
        super(context, R.layout.rowlayout_courses,data);
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.rowlayout_courses,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.rowlayout_course_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.rowlayout_course_description);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(!data.isEmpty()) {
            File file = data.get(position);
            StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(getContext());
            StudiousAndroidManifest sAM = sAFM.getManifestFromCourse(file);
            if(sAM!=null){
                String description = "Chapters: "+sAM.getChapterCount()+"\nCreated: "+sAM.getCreationDate();
                viewHolder.description.setText(description);
            }
            viewHolder.title.setText(file.getName());
            //viewHolder.description.setText(file.getAbsolutePath());
        }
        return convertView;
    }

    private static class ViewHolder{
        public TextView title;
        public TextView description;
    }

}
