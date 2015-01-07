package com.example.jkc.studiousx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jkc.studiousx.MiscAdapters.ColorSpinnerManager;
import com.example.jkc.studiousx.StudiousCore.ManifestScaffold;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
        TODO Allow spinner selection to actually add color data to the manifest
 */
public class EditCourse extends Activity {

    public static final String EXTRA_EDIT_COURSE_PATH = "com.jkc.studious.editcoursepath";

    private ViewHolder viewHolder;
    private ColorSpinnerManager colorSpinnerManager;
    private StudiousAndroidManifest originalManifest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Intent intent = getIntent();
        String filePath = intent.getStringExtra(ActivityCourseSelection.EXTRA_COURSE_PATH);
        prepareViewHolder();
        colorSpinnerManager = new ColorSpinnerManager(this,viewHolder.colorSpinner);
        if(filePath!=null){
            File file = new File(filePath);
            StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
            StudiousAndroidManifest manifest = sAFM.getManifestFromCourse(file);
            if(manifest!=null){
                //initialize fields with
                setTitle("Edit Course");
                originalManifest = manifest;
                viewHolder.nameField.setText(manifest.getName());
            }
        }else{
            setTitle("New Course");
        }
    }


    private void prepareViewHolder(){
        viewHolder = new ViewHolder();
        viewHolder.nameField = (EditText) findViewById(R.id.edit_course_field_name);
        viewHolder.colorSpinner = (Spinner) findViewById(R.id.spinner);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void closeButton(View view){
        finish();
    }

    public void acceptButton(View view){
        if(originalManifest!= null){
            String oldName = originalManifest.getName();
            originalManifest.setName(viewHolder.nameField.getText().toString());
            originalManifest.setColorMain(colorSpinnerManager.getHexString());
            StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
            sAFM.rewriteManifest(oldName,originalManifest);
        }else{
            ManifestScaffold scaffold = new ManifestScaffold();
            scaffold.name = viewHolder.nameField.getText().toString();
            scaffold.chapterCount = 0;
            scaffold.creationDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            scaffold.color = colorSpinnerManager.getHexString();
            StudiousAndroidManifest newManifest = StudiousAndroidManifest.createFromScaffold(scaffold);
            StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
            sAFM.createCourseFromManifest(newManifest);
        }
        finish();
    }

    static class ViewHolder{
        EditText nameField;
        Spinner colorSpinner;
    }
}
