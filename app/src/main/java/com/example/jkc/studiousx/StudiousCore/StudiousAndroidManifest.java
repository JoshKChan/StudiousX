package com.example.jkc.studiousx.StudiousCore;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joshua on 01/01/2015.
 */
public class StudiousAndroidManifest {

    public static final String TAG_MANIFEST = "manifest";
    public static final String TAG_NAME = "name";
    public static final String TAG_COLOR_MAIN = "color_main";
    public static final String TAG_CHAPTERS = "chapters";
    public static final String TAG_DATE_CREATION = "creation_date";

    private String name;
    private String colorMain;
    private int chapterCount;
    private String creationDate;

    public StudiousAndroidManifest(){
        chapterCount = 0;
        colorMain = "";
        creationDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public StudiousAndroidManifest(String iCreationDate){
        this();
        creationDate = iCreationDate;
    }

    public String getName(){
        return name;
    }

    public int getChapterCount(){
        return chapterCount;
    }

    public String getCreationDate(){
        return creationDate;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setColorMain(String color){
        colorMain = color;
    }

    public void setChapterCount(int newCount){
        chapterCount = newCount;
    }

    public static StudiousAndroidManifest createFromScaffold(ManifestScaffold scaffold){
        StudiousAndroidManifest manifest = null;
        if(scaffold!=null && scaffold.isValid()){
            manifest = new StudiousAndroidManifest(scaffold.creationDate);
            manifest.setName(scaffold.name);
            manifest.setChapterCount(scaffold.chapterCount);
            manifest.setColorMain(scaffold.color);
        }
        return manifest;
    }

    public String getXMLString(){
        String result = null;
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        try{
            xmlSerializer.setOutput(stringWriter);
            xmlSerializer.startDocument("UTF-8",true);
            xmlSerializer.startTag("",TAG_MANIFEST);
            //Course Name
            xmlSerializer.startTag("",TAG_NAME);
            xmlSerializer.text(name);
            xmlSerializer.endTag("", TAG_NAME);
            //Colors
            xmlSerializer.startTag("",TAG_COLOR_MAIN);
            xmlSerializer.text(colorMain);
            xmlSerializer.endTag("", TAG_COLOR_MAIN);
            //Chapter Count
            xmlSerializer.startTag("",TAG_CHAPTERS);
            xmlSerializer.text(Integer.toString(chapterCount));
            xmlSerializer.endTag("", TAG_CHAPTERS);
            //Date created
            xmlSerializer.startTag("",TAG_DATE_CREATION);
            xmlSerializer.text(creationDate);
            xmlSerializer.endTag("", TAG_DATE_CREATION);
            xmlSerializer.endTag("", TAG_MANIFEST);
            xmlSerializer.endDocument();
            result = stringWriter.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Used to see if a manifest represents a valid course that could be used
     * to create a course folder.
     * The necessary requirements is that the manifest itself must exist, and that
     * it must have a valid name.
     * @param manifest  The manifest to check.
     * @return          Returns true if the manifest is valid, false if it is not.
     */
    public static boolean isValid(StudiousAndroidManifest manifest){
        boolean valid = false;
        if(manifest!=null
                && manifest.isColorMainValid()
                && manifest.getName()!=null
                && !manifest.getName().isEmpty()){
            valid = true;
        }
        return valid;
    }

    public int getColorAsInt(){
        return Integer.parseInt(colorMain.substring(colorMain.indexOf("0x")),16);
    }

    public String getColorString(){
        return colorMain;
    }

    //Colour just need not be null to avoid crashing. In cases where blank colors are found, defaults are used.
    private boolean isColorMainValid(){
        boolean valid = false;
        if(colorMain!=null){
            valid = true;
        }
        return valid;
    }

}
