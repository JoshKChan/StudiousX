package com.example.jkc.studiousx;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jkc.studiousx.ListAdapters.CourseAdapter;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;


public class ActivityCourses extends ListActivity {

    private TextView logTV;
    private EditText editText;

    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        //Set adapter
        StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
        courseAdapter = new CourseAdapter(this,sAFM.getCourseFiles());
        setListAdapter(courseAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_courses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addPress(View view){
        String text = editText.getText().toString();
        editText.setText("");
        StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
        sAFM.createCourse(text);
    }

}
