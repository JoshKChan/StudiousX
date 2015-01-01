package com.example.jkc.studiousx.StudiousCore;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Joshua on 21/12/2014.
 *
 * Looks like I/O in Android requires access to a Context object.
 * Will have to instantiate instances of SAFM from Activity classes.
 *
 *  Using ,getExternalFilesDir(null) for file path
 *
 */
public class StudiousAndroidFileManager extends StudiousFileManager {

    private Context context;

    public StudiousAndroidFileManager(Context context){
        this.context = context;
    }

    public void initialize(){

    }



    //Android Interfacing ////////////////////////////////////////////////////

    private void debugPrint(String message){
        Log.e("SAFM",message);
    }

    public File getBaseDir(){
        return context.getExternalFilesDir(null);
    }

    public File getCoursesDir(){
        File coursesFolder = new File(getBaseDir().getAbsolutePath()+COURSES_PATH_EXT);
        if(!coursesFolder.exists()){
            coursesFolder.mkdir();
        }
        return coursesFolder;
    }

    /*
        Returns the status of the attempt
        0 = Successful folder creation
        1 = Operation failed
        2 = Folder with same name already exists

        Create a new folder aimed at the courses directory with name specified in the parameter.
        If such a folder already exists, return 2. Otherwise attempt to create the folder.
        If we can create it, return 0. If mkdir fails, return 1.

     */
    public int createCourse(String name){
        int out = 0;
        File newCourse = new File(getCoursesDir()+"/"+name);
        if(!newCourse.exists()){
            if(newCourse.mkdir()){
                out = 0;
            }else{
                out = 1;
            }
        }else{
            out = 2;
        }
        return out;
    }

    //Returns a collection of all the files in courses
    public ArrayList<File> getCourseFiles(){
        return new ArrayList<File>(Arrays.asList(getCoursesDir().listFiles()));
    }

    //TODO Keep an eye on this. Seems like it works. Tested with directories and subdirectories. NOT tested with actual files.
    public boolean deleteFile(File file){
        boolean success = false;
        if(file!=null && file.exists()){
            if(file.isDirectory()){
                File[] files = file.listFiles();
                if(files!=null){
                    for (File f: files){
                        deleteFile(f);
                    }
                    success = file.delete();
                }
            }else{
                success = file.delete();
            }
        }
        return success;
    }

}
