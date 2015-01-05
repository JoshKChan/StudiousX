package com.example.jkc.studiousx.StudiousCore;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
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

        //TODO Refector consideration: Return the File obj created instead?...
            Would be easier to add to array-adapter in relevant Activities.
                But then lose error feedback?
                    -Solution: Track error feedback in log file
     */
    public int createCourse(String name){
        int out = 0;
        File newCourse = new File(getCoursesDir()+"/"+name);
        if(!newCourse.exists()){
            if(newCourse.mkdir()){
                if(writeManifest(newCourse,name)){
                    out = 0;
                }
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

    /**
     * Return a file with matching name.
     *
     * @param name Name of the directory to look return.
     * @return     The directory with matching name.
     *             Null if the directory cannot be found.
     */
    public File findCourseDir(String name){
        File file = null;
        ArrayList<File> files = getCourseFiles();
        if(files!=null && !files.isEmpty()){
            for (File f:files){
                if(f.getName().equals(name)){
                    file = f;
                    break;
                }
            }
        }
        return file;
    }

    //TODO renameCourse

    /**
     * Changes both the name of a course's directory and the name stored
     * in that directory's manifest file.
     *
     * @param course    The name of the course before changing the name.
     * @param newName   The new name for the course.
     */
    public void renameCourse(String course, String newName){

    }

    public StudiousAndroidManifest getManifestFromCourse(File inFile){
        StudiousAndroidManifest manifest = null;
        File file = new File(inFile.getAbsolutePath()+"/manifest.xml");
        if(file!=null && file.exists()){
            if(file.getAbsolutePath().contains(getCoursesDir().getAbsolutePath())){
                try {
                    FileInputStream inStream = new FileInputStream(file);
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(inStream,null);
                    manifest = generateManifest(parser);
                    inStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Log.e("sAFM-getManifestFromCourse","File not in courses dir\n"+file.getAbsolutePath()+"\n"+getCoursesDir().getAbsolutePath());
            }
        }
        return manifest;
    }

    //TODO Clean
    private StudiousAndroidManifest generateManifest(XmlPullParser parser){
        StudiousAndroidManifest manifest = null;
        ManifestScaffold scaffold = new ManifestScaffold();
        try {
            int eventType = parser.next();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_TAG && !parser.getName().equals(StudiousAndroidManifest.TAG_MANIFEST)){
                   String tagName = parser.getName();
                    eventType = parser.next();
                    if(eventType == XmlPullParser.TEXT) {
                        String tagValue = parser.getText();
                        if (tagName.equals(StudiousAndroidManifest.TAG_NAME)) {
                            scaffold.name = tagValue;
                        } else if (tagName.equals(StudiousAndroidManifest.TAG_CHAPTERS)) {
                            scaffold.chapterCount = Integer.parseInt(tagValue);
                        } else if (tagName.equals(StudiousAndroidManifest.TAG_DATE_CREATION)) {
                            scaffold.creationDate = tagValue;
                        }
                    }
                }//if start of tag
                eventType = parser.next();
            }//while
        }catch (Exception e){
            e.printStackTrace();
        }
        if(scaffold.isValid()){
            manifest = StudiousAndroidManifest.createFromScaffold(scaffold);
        }
        return manifest;
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

    private boolean writeManifest(File file, String name){
        boolean success = false;
        //Manifest
        StudiousAndroidManifest manifest = new StudiousAndroidManifest();
        manifest.setName(name);
        String xml = manifest.getXMLString();

        try {
            File outputFile = new File(file.getAbsolutePath(), "manifest.xml");
            Writer writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(xml);
            writer.close();
            Toast.makeText(context,"Course created successfully",Toast.LENGTH_SHORT).show();
            success = true;
        }catch (Exception e){
            Toast.makeText(context,"Error creating course...",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return success;
    }

}
