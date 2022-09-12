package com.example.googlesigninwith_firebase_task;


import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class ModelClass implements Serializable {

    public ModelClass(String title, String desc) {

        this.title = title;
        this.desc = desc;
    }

    public ModelClass( String title, String desc, String img,String id) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public ModelClass(String img) {
        this.img=img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Exclude
    public String id;
    public String title;
    public String desc;
   public String img;

    public  ModelClass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

//    public  ModelClass(String id,String title, String desc) {
//        this.id=id;
//        this.title = title;
//        this.desc = desc;
//
//    }


    public ModelClass(String title, String desc, String img) {
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}




