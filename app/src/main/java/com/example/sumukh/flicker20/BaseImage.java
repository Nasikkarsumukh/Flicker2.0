package com.example.sumukh.flicker20;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseImage {
    //Base image object
    private String url;
    public BaseImage(){}
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public static BaseImage fromJson(JSONObject jsonObject) {

        BaseImage baseImage = new BaseImage();

        try {
            baseImage.setUrl(jsonObject.getString("url_m"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return baseImage;
    }
}
