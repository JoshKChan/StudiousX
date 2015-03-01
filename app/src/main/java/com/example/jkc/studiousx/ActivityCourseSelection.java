package com.example.jkc.studiousx;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jkc.studiousx.ListAdapters.CourseAdapter;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidManifest;
import com.example.jkc.studiousx.Support.HierarchyRecord;

import java.io.File;

public class ActivityCourseSelection extends ListActivity {

    private static final int REQUEST_NEW_COURSE = 100;
    private static final int REQUEST_EDIT_COURSE = 101;

    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseselection);
        StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
        courseAdapter = new CourseAdapter(this,sAFM.getCourseFiles());
        setListAdapter(courseAdapter);
        AdapterView.OnItemClickListener listItemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                openCourse(position);
            }
        };
        ListView listview = getListView();
        listview.setOnItemClickListener(listItemClick);
        registerForContextMenu(listview);
    }

    @Override
    public void onResume(){
        super.onResume();
        courseAdapter.clear();
        courseAdapter.addAll(new StudiousAndroidFileManager(this).getCourseFiles());
        courseAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        File file = courseAdapter.getItem(acmi.position);
        if(file != null){
            menu.setHeaderTitle(file.getName());
        }else{
            menu.setHeaderTitle("??? " + acmi.position);
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_courses,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        int listItemIndex = info.position;
        switch (menuItemIndex){
            case R.id.courses_contextmenu_open:
                openCourse(listItemIndex);
                break;
            case R.id.courses_contextmenu_edit:
                editCourse(listItemIndex);
                break;
            case R.id.courses_contextmenu_delete:
                deleteCourse(listItemIndex);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_courseselection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_newCourse) {
            createNewCourse();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_NEW_COURSE){
            String toastText = "Undefined result creating course";
            if(resultCode == Activity.RESULT_OK){
                StudiousAndroidManifest newManifest = data.getParcelableExtra(EditCourse.EXTRA_MANIFEST_OUT);
                if(StudiousAndroidManifest.isValid(newManifest)){
                    StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
                    sAFM.createCourseFromManifest(newManifest);
                    courseAdapter.add(newManifest);
                    courseAdapter.notifyDataSetChanged();
                    toastText = "Course created";
                }else{
                    toastText = "Error creating course, invalid manifest";
                }
            }else if(resultCode == Activity.RESULT_CANCELED){
                toastText = "Error creating course";
            }
            Toast.makeText(this,toastText,Toast.LENGTH_SHORT).show();
        }else if(requestCode == REQUEST_EDIT_COURSE){
            String toastText = "Undefined result editing course";
            if(resultCode == Activity.RESULT_OK){
                StudiousAndroidManifest newManifest = data.getParcelableExtra(EditCourse.EXTRA_MANIFEST_OUT);
                if(StudiousAndroidManifest.isValid(newManifest)){
                    String oldCourseName = data.getStringExtra(EditCourse.EXTRA_MANIFEST_OLD);
                    StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
                    sAFM.rewriteManifest(oldCourseName,newManifest);
                    File oldCourse = sAFM.findCourseDir(oldCourseName);
                    courseAdapter.remove(oldCourse);
                    courseAdapter.add(sAFM.findCourseDir(newManifest.getName()));
                    courseAdapter.notifyDataSetChanged();
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

    public void createNewCourse(){
        Intent intent = new Intent(this,EditCourse.class);
        startActivityForResult(intent,REQUEST_NEW_COURSE);
    }

    public void openCourse(int index){
        File file = courseAdapter.getItem(index);
        Intent intent = new Intent(this, ActivityCourse.class);
        StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
        HierarchyRecord record = new HierarchyRecord(sAFM.getCoursesDirPath());
        record.setCourseName(file.getName());
        intent.putExtra(ActivityCourse.EXTRA_HEIRARCHY_RECORD,record);
        startActivity(intent);
    }

    public void editCourse(int index){
        File file = courseAdapter.getItem(index);
        Intent intent = new Intent(this,EditCourse.class);
        intent.putExtra(ActivityCourse.EXTRA_COURSE_PATH,file.getAbsolutePath());
        startActivityForResult(intent,REQUEST_EDIT_COURSE);
    }

    public void deleteCourse(int index){
        File file = courseAdapter.getItem(index);
        StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
        boolean success = sAFM.deleteFile(file);
        if(success){
            Toast.makeText(this,"Course deleted successfully",Toast.LENGTH_SHORT).show();
            courseAdapter.remove(file);
            courseAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this,"Error during deletion",Toast.LENGTH_SHORT).show();
        }
    }

}
