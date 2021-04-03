package com.chanaka.onlinelibrary.dbhadler;

public  class UserModel  {

  private      String Id;
  private      String name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel() {
    }


    public static   String dupId;
    public static   String dupname;

    public static String getDupId() {
        return dupId;
    }

    public static void setDupId(String dupId) {
        UserModel.dupId = dupId;
    }

    public static String getDupname() {
        return dupname;
    }

    public static void setDupname(String dupname) {
        UserModel.dupname = dupname;
    }


}
