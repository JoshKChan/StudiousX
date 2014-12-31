package com.example.jkc.studiousx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jkc.studiousx.StudiousCore.StudiousAndroidFileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


/*
    You were figuring out to how create/manage files on external memory.
    https://developer.android.com/guide/topics/data/data-storage.html#AccessingExtFiles

    You were building the Home activity interface so you could begin to test Course creation.

 */
public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public void clickEvent_courses(View view){
        Toast.makeText(this,"Courses",Toast.LENGTH_SHORT).show();
    }

    public void clickEvent_settings(View view){
        Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
    }

    private void initialize(){
        externalAttempt();
        //StudiousAndroidFileManager safm = new StudiousAndroidFileManager(this);
        //safm.initialize();
        Intent intent = new Intent(this, ActivityCourses.class);
        startActivity(intent);
    }

    private void internalAttempt(){

    }

    private void externalAttempt(){
        debugPrint("Init begin");
        String state = Environment.getExternalStorageState();
        debugPrint(state);
        //TextView t = (TextView)findViewById(R.id.home_text);
        //t.setText(state);
    }

    private void debugPrint(String text){
        Log.e("Home",">>"+text);
    }
}
