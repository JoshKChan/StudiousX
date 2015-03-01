package com.example.jkc.studiousx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
        Intent intent = new Intent(this, ActivityCourseSelection.class);
        startActivity(intent);
    }

}
