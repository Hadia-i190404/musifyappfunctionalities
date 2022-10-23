package com.hadianoor.i190404_i190405;

public class MyModel {

    String fname, lname, gender, bio;

    public MyModel(String fname, String lname, String gender, String bio) {
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.bio = bio;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}