package com.example.jkc.studiousx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;

import java.io.File;
import java.util.Collection;


public class ActivityCourses extends Activity {

    private TextView logTV;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        initialize();
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
        log(text);
        editText.setText("");

        String message = "--=New Course=--\n";

        StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
        message+=text+" : "+sAFM.createCourse(text)+"\n";
        Collection<File> folders = sAFM.getCourseFiles();
        if(folders!=null){
            for(File f: folders){
                message+=f.getAbsolutePath()+"\n";
            }
        }
        log(message);
    }

    /*
        Every time this activity is opened/returned to(focused?) do...
     */
    private void initialize(){
        //see if Courses folder exists
        //Read contents of Courses folder
        //Display contents of Courses folder
        logTV = (TextView)findViewById(R.id.courses_textview_log);
        editText = (EditText)findViewById(R.id.courses_edittext);

        StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
        File file = sAFM.getBaseDir();
        File coursesFolder = sAFM.getCoursesDir();
        Log.e("701",file.getAbsolutePath());
        Log.e("701",coursesFolder.getAbsolutePath());
    }

    private void log(String text){
        logTV.setText(text+"\n"+logTV.getText());
    }

}
