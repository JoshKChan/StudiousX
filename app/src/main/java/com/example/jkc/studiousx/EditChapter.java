package com.example.jkc.studiousx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.jkc.studiousx.StudiousCore.ChapterInfo;

public class EditChapter extends Activity {

    public static final String EXTRA_CHAPTERINFO_IN = "com.jkc.studious.CHAPTER_INFO_EXTRA_IN";
    public static final String EXTRA_CHAPTERINFO_RESULT = "com.jkc.studious.CHAPTER_INFO_EXTRA_OUT";

    private ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chapter);
        ChapterInfo chapterInfo = getIntent().getParcelableExtra(EXTRA_CHAPTERINFO_IN);
        prepareViewHolder();
        populateFields(chapterInfo);
    }

    private void prepareViewHolder(){
        viewHolder = new ViewHolder();
        viewHolder.nameField = (EditText) findViewById(R.id.edit_chapter_field_name);
    }

    private void populateFields(ChapterInfo chapterInfo){
        if(chapterInfo != null){
            String name = chapterInfo.getName();
            if(name!=null&&!name.isEmpty()){
                viewHolder.nameField.setText(name);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_chapter, menu);
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

    public void closeButton(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void acceptButton(View view){
        Intent intent = new Intent();
        ChapterInfo chapterInfo = new ChapterInfo();
        String newName = viewHolder.nameField.getText().toString();
        if(newName!=null&&!newName.isEmpty()){
            chapterInfo.setName(newName);
        }



        intent.putExtra(EXTRA_CHAPTERINFO_RESULT,chapterInfo);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private static class ViewHolder{
        EditText nameField;
    }
}