package com.rasheek.iconnect.Model;

import java.util.Date;

public class User {


    String name;
    String mobile;
    String nic;
    String email;
    String gender;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    String profileImage;

    public User(String name, String mobile, String nic, String email, String gender, String profileImage) {
        this.name = name;
        this.mobile = mobile;
        this.nic = nic;
        this.email = email;
        this.gender = gender;
        this.profileImage =  profileImage;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
