package com.example.jkc.studiousx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;

import org.w3c.dom.Text;

import java.io.File;


public class ActivityCourse extends Activity {

    private StudiousAndroidManifest manifest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Intent intent = getIntent();
        String path = intent.getStringExtra(ActivityCourseSelection.EXTRA_COURSE_PATH);
        File file = new File(path);
        if(file.exists()) {
            StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
            setTitle(file.getName());

            manifest = sAFM.getManifestFromCourse(file);
            if(manifest!=null){

            }

        }
    }

    @Override
    public void onResume(){
        super.onResume();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_editCourse){
            if(manifest!=null) {
                File file = new StudiousAndroidFileManager(this).findCourseDir(manifest.getName());
                Intent intent = new Intent(this, EditCourse.class);
                intent.putExtra(ActivityCourseSelection.EXTRA_COURSE_PATH,file.getAbsolutePath());
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
