package com.example.testempoyes.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

@Entity(tableName = "employees")

public class Employee {
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    @PrimaryKey(autoGenerate = true)
    public int id;
    @SerializedName("f_name")
    public String fName;
    @SerializedName("l_name")
    public String lName;
    @SerializedName("birthday")
    public String birthday;
    @SerializedName("avatr_url")
    public String avatrUrl;
//    @SerializedName("specialty")
//    @Expose
//    private List<Specialty> specialty=null;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatrUrl() {
        return avatrUrl;
    }

    public void setAvatrUrl(String avatrUrl) {
        this.avatrUrl = avatrUrl;
    }

}
