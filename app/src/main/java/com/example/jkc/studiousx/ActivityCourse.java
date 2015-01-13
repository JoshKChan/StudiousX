package com.example.jkc.studiousx;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jkc.studiousx.ListAdapters.ChapterAdapter;
import com.example.jkc.studiousx.StudiousCore.Chapter;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;


public class ActivityCourse extends ListActivity {

    private static final int REQUEST_EDIT_COURSE = 100;

    private StudiousAndroidManifest manifest;
    private ChapterAdapter chapterAdapter;

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
                chapterAdapter = new ChapterAdapter(this,sAFM.getCourseChapters(manifest.getName()));
                setListAdapter(chapterAdapter);
                //chapterAdapter = new ChapterAdapter(this,);
            }else{
                Toast.makeText(this,"Error opening course",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            Toast.makeText(this,"Error opening course",Toast.LENGTH_SHORT).show();
            finish();
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
                    toastText = "Changes to course saved";
                }else{
                    toastText = "Error editing course, invalid manifest";
                }
            }else if(resultCode == Activity.RESULT_CANCELED){
                toastText = "Error editing course";
            }
            Toast.makeText(this,toastText,Toast.LENGTH_SHORT).show();
        }
    }

    public void addPress(View view){
        String name = ((EditText)findViewById(R.id.chapters_edittext)).getText().toString();
        if(name!=null && !name.isEmpty()){
            Chapter chapter = new Chapter();
            chapter.setName(name);
            chapter.setNumber(0);
            StudiousAndroidFileManager studiousAndroidFileManager = new StudiousAndroidFileManager(this);
            studiousAndroidFileManager.saveChapter(chapter,manifest);
            chapterAdapter.add(chapter);
            chapterAdapter.notifyDataSetChanged();
            Log.w("ActivityCourse","Completed Add event");
        }
    }
}
