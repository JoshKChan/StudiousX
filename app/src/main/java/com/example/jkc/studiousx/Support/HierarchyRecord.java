package com.example.jkc.studiousx.Support;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joshua on 19/01/2015.
 *
 * HierarchyRecord is parcelable data container used to store strings (representing file paths) that represent
 * the user's navigation down Courses, Chapters, and Questions.
 *
 */
public class HierarchyRecord implements Parcelable {

    private String courseDir;
    private String courseName;
    private  String chapterName;

    public HierarchyRecord(){
        courseDir = null;
        courseName = null;
        chapterName = null;
    }

    public HierarchyRecord(String courseDir){
        this();
        this.courseDir = courseDir;
    }

    public HierarchyRecord(HierarchyRecord other){
        this.courseDir = other.courseDir;
        this.courseName = other.courseName;
        this.chapterName = other.chapterName;
    }

    public String getCourseName(){
        return courseName;
    }

    public String getChapterName(){
        return chapterName;
    }

    public String getCourseDir(){
        return courseDir;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setChapterName(String chapterName){
        this.chapterName = chapterName;
    }

    public String getCoursePath(){
        return courseDir+"/"+courseName;
    }

    public String getChapterPath(){
        return getCoursePath()+"/"+chapterName+".ch";
    }


    //////////////////////////////////////Parcelable Interface

    private HierarchyRecord(Parcel in){
        this.courseDir = in.readString();
        this.courseName = in.readString();
        this.chapterName = in.readString();
    }

    @Override
    public int describeContents(){return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(courseDir);
        parcel.writeString(courseName);
        parcel.writeString(chapterName);
    }

    public static final Creator<HierarchyRecord> CREATOR
            = new Creator<HierarchyRecord>(){
        public HierarchyRecord createFromParcel(Parcel in){return new HierarchyRecord(in);}
        public HierarchyRecord[] newArray(int size){return new HierarchyRecord[size];}
    };

}
