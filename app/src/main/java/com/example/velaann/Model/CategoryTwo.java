package com.example.velaann.Model;

import android.net.Uri;

public class CategoryTwo {
    private String dataName;
    private String dataId;
    private String image;
    public CategoryTwo() {
    }

    public CategoryTwo(String dataName, String dataId, String image) {
        this.dataName = dataName;
        this.dataId = dataId;
        this.image = image;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
