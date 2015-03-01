package com.example.jkc.studiousx;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jkc.studiousx.ListAdapters.ChapterAdapter;
import com.example.jkc.studiousx.StudiousCore.Chapter;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;
import com.example.jkc.studiousx.Support.HierarchyRecord;

import java.io.File;


public class ActivityCourse extends ListActivity {

    public static final String EXTRA_COURSE_PATH = "com.jkc.studious.EXTRA_COURSE_PATH";
    public static final String EXTRA_HEIRARCHY_RECORD = "com.jkc.studious.EXTRA_RECORD";
    private static final int REQUEST_EDIT_COURSE = 100;

    private StudiousAndroidManifest manifest;
    private HierarchyRecord hierarchyRecord;
    private ChapterAdapter chapterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Intent intent = getIntent();
        hierarchyRecord = intent.getParcelableExtra(EXTRA_HEIRARCHY_RECORD);
        String path = hierarchyRecord.getCoursePath();
        if(path!=null){
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
        }else {
            Toast.makeText(this,"Unable to load course...",Toast.LENGTH_SHORT).show();
            finish();
        }

        AdapterView.OnItemClickListener listItemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                openChapter(position);
            }
        };
        ListView listView = getListView();
        listView.setOnItemClickListener(listItemClick);
        registerForContextMenu(listView);
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
                intent.putExtra(EXTRA_COURSE_PATH,file.getAbsolutePath());
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
                    hierarchyRecord.setCourseName(manifest.getName());
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

    //Create a path that leads to the chapterName file. Pass that path as an extra.
    //ChapterActivity uses this path to try and load a chapterName.
    private void openChapter(int index){
        Chapter chapter = chapterAdapter.getItem(index);
        Intent intent = new Intent(this,ActivityChapter.class);
        hierarchyRecord.setChapterName(chapter.getName());
        intent.putExtra(EXTRA_HEIRARCHY_RECORD, hierarchyRecord);
        intent.putExtra(ActivityChapter.MANIFEST_EXTRA,manifest);
        startActivity(intent);
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
