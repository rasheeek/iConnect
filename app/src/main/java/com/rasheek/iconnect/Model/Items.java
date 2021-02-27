package com.rasheek.iconnect.Model;

public class Items {
    String name;
    String desc;
    int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Items(String name, String desc, int image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    public Items() {
    }
}
