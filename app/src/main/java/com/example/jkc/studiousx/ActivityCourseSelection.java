package com.example.jkc.studiousx;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jkc.studiousx.ListAdapters.CourseAdapter;
import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;

import java.io.File;


public class ActivityCourseSelection extends ListActivity {

    public static final String EXTRA_COURSE_PATH = "com.jkc.studious.EXTRA_COURSE_PATH";

    private EditText editText;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseselection);
        editText = (EditText)findViewById(R.id.courses_edittext);
        //Set adapter//////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////
        StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
        courseAdapter = new CourseAdapter(this,sAFM.getCourseFiles());
        setListAdapter(courseAdapter);
        //Set list-view mouse listeners////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////
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

    /*
        Update the contents of the courseAdapter by clearing it then repopulating it.
            Better to: Get list of files then compare that to set of files in adapter. Add the disjoint to the courseAdapter. Faster or not...?
     */
    @Override
    public void onResume(){
        super.onResume();
        courseAdapter.clear();
        courseAdapter.addAll(new StudiousAndroidFileManager(this).getCourseFiles());
        courseAdapter.notifyDataSetChanged();
    }

    /*
        Defines what happens when a context menu is created.
        Note that there is no variation dependant on the view that invokes the context menu, since there ought not to be anything that
        invokes a context menu except for the ListView.

        Set the title of the menu to the name of the file, "???" if for some reason no file is retrieved.
        Then inflate the rest of the menu using its XML layout.
     */
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

    //TODO Remove debug information // onContextItemSelected
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        int listItemIndex = info.position;
        String message = listItemIndex+" Default:"+menuItemIndex;
        switch (menuItemIndex){
            case R.id.courses_contextmenu_open:
                message = (listItemIndex+" Open "+menuItemIndex);
                openCourse(listItemIndex);
                break;
            case R.id.courses_contextmenu_edit:
                message = (listItemIndex+" Edit "+menuItemIndex);
                editCourse(listItemIndex);
                break;
            case R.id.courses_contextmenu_delete:
                message = (listItemIndex+" Delete "+menuItemIndex);
                deleteCourse(listItemIndex);
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    public void createNewCourse(){
        Intent intent = new Intent(this,EditCourse.class);
        startActivity(intent);
    }

    public void openCourse(int index){
        File file = courseAdapter.getItem(index);
        Intent intent = new Intent(this, ActivityCourse.class);
        intent.putExtra(EXTRA_COURSE_PATH,file.getAbsolutePath());
        startActivity(intent);
    }

    public void editCourse(int index){
        File file = courseAdapter.getItem(index);
        Intent intent = new Intent(this,EditCourse.class);
        intent.putExtra(EXTRA_COURSE_PATH,file.getAbsolutePath());
        startActivity(intent);
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

    public void addPress(View view){
        String text = editText.getText().toString();
        if(text!=null&&!text.isEmpty()) {
            editText.setText("");
            //Directory creation
            StudiousAndroidFileManager sAFM = new StudiousAndroidFileManager(this);
            int result = sAFM.createCourse(text);
            if(result == 0) {
                File file = sAFM.findCourseDir(text);
                courseAdapter.add(file);
                courseAdapter.notifyDataSetChanged();
            }
        }
    }

}
