package com.example.jkc.studiousx.StudiousCore;

/**
 * Created by Joshua on 04/01/2015.
 */
public class ManifestScaffold {

        public String name;
        public int chapterCount = -1;
        public String creationDate;
        public String color;

        public ManifestScaffold(){
            name = null;
            chapterCount = -1;
            creationDate = null;
            color = "";
        }

        public boolean isValid(){
            return (name!=null&&!name.isEmpty()
                    &&chapterCount>=0
                    &&creationDate!=null&&!creationDate.isEmpty());
        }

        @Override
        public String toString(){
            String out = name+"<>"+chapterCount+"<>"+creationDate;
            if(color!=null){
                out+="<>"+color;
            }
            return out;
        }

}
