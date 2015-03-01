package com.example.jkc.studiousx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jkc.studiousx.StudiousCore.Chapter;
import com.example.jkc.studiousx.StudiousCore.ChapterInfo;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;
import com.example.jkc.studiousx.Support.HierarchyRecord;

public class ActivityChapter extends Activity {

    public static final String MANIFEST_EXTRA = "com.jkc.studious.ActivityChapter.MANIFEST";
    private static final int REQUEST_EDIT_CHAPTER = 100;

    private Chapter chapter;
    private HierarchyRecord hierarchyRecord;
    private StudiousAndroidManifest manifest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        hierarchyRecord = getIntent().getParcelableExtra(ActivityCourse.EXTRA_HEIRARCHY_RECORD);
        String path = hierarchyRecord.getChapterPath();
        manifest = getIntent().getParcelableExtra(MANIFEST_EXTRA);
        if(path!=null&&!path.isEmpty()){
            StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
            chapter = sAFM.loadChapter(path);
            populateUI();
        }else{
            Toast.makeText(this,"Error loading chapterName going back...",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void populateUI(){
        if(chapter!=null){
            //TODO populate fields
            setTitle(chapter.getName());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_chapter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.action_editChapter:
                intent = new Intent(this,EditChapter.class);
                intent.putExtra(EditChapter.EXTRA_CHAPTERINFO_IN,chapter.produceChapterInfo());
                startActivityForResult(intent, REQUEST_EDIT_CHAPTER);
                return true;
            case android.R.id.home:
                intent = new Intent(this,ActivityCourse.class);
                intent.putExtra(ActivityCourse.EXTRA_HEIRARCHY_RECORD, hierarchyRecord);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_EDIT_CHAPTER){
            if(resultCode == Activity.RESULT_OK){
                String oldChapterName = chapter.getName();
                ChapterInfo chapterInfo = data.getParcelableExtra(EditChapter.EXTRA_CHAPTERINFO_RESULT);
                chapter.updateInfo(chapterInfo);
                populateUI();
                StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
                sAFM.saveChapter(chapter,manifest,oldChapterName);
                Toast.makeText(this,"Chapter updated",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Changes discarded",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
