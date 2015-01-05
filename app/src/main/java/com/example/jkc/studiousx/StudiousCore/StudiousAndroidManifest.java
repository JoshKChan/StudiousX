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
    public static final String TAG_CHAPTERS = "chapters";
    public static final String TAG_DATE_CREATION = "creation_date";

    private String name;
    private int chapterCount;
    private String creationDate;

    public StudiousAndroidManifest(){
        this.name = name;
        chapterCount = 0;
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

    public void setChapterCount(int newCount){
        chapterCount = newCount;
    }

    public static StudiousAndroidManifest createFromScaffold(ManifestScaffold scaffold){
        StudiousAndroidManifest manifest = null;
        if(scaffold!=null && scaffold.isValid()){
            manifest = new StudiousAndroidManifest(scaffold.creationDate);
            manifest.setName(scaffold.name);
            manifest.setChapterCount(scaffold.chapterCount);
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

}
