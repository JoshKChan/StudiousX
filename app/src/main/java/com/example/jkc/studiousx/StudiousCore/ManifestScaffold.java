package com.example.jkc.studiousx.StudiousCore;

/**
 * Created by Joshua on 04/01/2015.
 */
public class ManifestScaffold {

        public String name;
        public int chapterCount = -1;
        public String creationDate;

        public ManifestScaffold(){
            name = null;
            chapterCount = -1;
            creationDate = null;
        }

        public boolean isValid(){
            return (name!=null&&!name.isEmpty()
                    &&chapterCount>=0
                    &&creationDate!=null&&!creationDate.isEmpty());
        }

        @Override
        public String toString(){
            return name+"<>"+chapterCount+"<>"+creationDate;
        }

}
