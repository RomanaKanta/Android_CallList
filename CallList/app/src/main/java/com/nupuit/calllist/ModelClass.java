package com.nupuit.calllist;

/**
 * Created by tanvir-android on 8/11/16.
 */
public class ModelClass {

    String id;
          String name;
    String number;


    public ModelClass(){}

    public ModelClass(String id, String name, String num) {
        this.id = id;
        this.name = name;
        this.number = num;
    }
    public ModelClass( String name, String num) {
        this.name = name;
        this.number = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
