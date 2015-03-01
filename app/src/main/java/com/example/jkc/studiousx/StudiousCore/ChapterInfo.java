package com.example.jkc.studiousx.StudiousCore;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joshua on 12/01/2015.
 *
 * Data container class to transport data describing the changes in a Chapter.
 * A null value implies there was no change. To be used with Chapter's update(ChapterInfo info) method.
 * Should NOT be used as a scaffold since null values are allowed here. Non-null values only reflect changes.
 */
public class ChapterInfo implements Parcelable {

    private String name;
    private String description;
    private int number;

    public ChapterInfo(){
        name=null;
        description=null;
        number = -1;
    }

    private ChapterInfo(Parcel in){
        this();
        name = in.readString();
        description = in.readString();
        number = in.readInt();
    }

    public String getName(){return name;}
    public String getDescription(){return description;}
    public int getNumber(){return number;}

    public void setName(String in){name = in;}
    public void setDescription(String in){description = in;}
    public void setNumber(int in){
        number = in;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        //parcel.writeStuff
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(number);
    }

    public static final Parcelable.Creator<ChapterInfo> CREATOR
            = new Parcelable.Creator<ChapterInfo>(){
        public ChapterInfo createFromParcel(Parcel in){return new ChapterInfo(in);}
        public ChapterInfo[] newArray(int size){return new ChapterInfo[size];}
    };

}
