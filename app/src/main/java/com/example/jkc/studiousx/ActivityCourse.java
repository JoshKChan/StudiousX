package com.example.jkc.studiousx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;

import org.w3c.dom.Text;

import java.io.File;


public class ActivityCourse extends Activity {

    private static final int REQUEST_EDIT_COURSE = 100;

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
                startActivityForResult(intent,REQUEST_EDIT_COURSE);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_EDIT_COURSE){
            String toastText = "Undefined result editing course";
            if(resultCode == Activity.RESULT_OK){
                StudiousAndroidManifest newManifest = data.getParcelableExtra(EditCourse.EXTRA_MANIFEST_OUT);
                if(StudiousAndroidManifest.isValid(newManifest)){
                    StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
                    sAFM.rewriteManifest(manifest.getName(),newManifest);
                    manifest = newManifest;
                    setTitle(manifest.getName());
                }else{
                    toastText = "Error editing course, invalid manifest";
                }
                toastText = "Changes to course saved";
            }else if(resultCode == Activity.RESULT_CANCELED){
                toastText = "Error editing course";
            }
            Toast.makeText(this,toastText,Toast.LENGTH_SHORT).show();
        }
    }
}
