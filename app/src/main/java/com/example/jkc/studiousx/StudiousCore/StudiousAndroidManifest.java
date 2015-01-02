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

    private static final String TAG_MANIFEST = "manifest";
    private static final String TAG_NAME = "name";
    private static final String TAG_CHAPTERS = "chapters";
    private static final String TAG_DATE_CREATION = "creation_date";

    private String name;
    private int chapterCount;
    private String creationDate;

    public StudiousAndroidManifest(String name){
        this.name = name;
        chapterCount = 0;
        creationDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public String getXMLString(){
        String result = null;
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        try{
            xmlSerializer.setOutput(stringWriter);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
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
