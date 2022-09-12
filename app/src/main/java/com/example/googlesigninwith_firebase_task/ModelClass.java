package com.example.googlesigninwith_firebase_task;




public class ModelClass  {

    public ModelClass(String title, String desc) {

        this.title = title;
        this.desc = desc;
    }

    public ModelClass(String id, String title, String desc, String img,String uid) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.uid=uid;
    }

    public ModelClass(String img) {
        this.img=img;
    }

    public ModelClass(String id1, String titlestr, String descstr, String uid) {
        this.id=id1;
        this.title=titlestr;
        this.desc=descstr;
        this.uid=uid;
    }

    public ModelClass(String titlestr, String descstr, String uid) {
        this.title=titlestr;
        this.desc=descstr;
        this.uid=uid;
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

    public String id;
    public String title;
    public String desc;
   public String img;
 public  String uid;
    public  ModelClass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }




    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}




