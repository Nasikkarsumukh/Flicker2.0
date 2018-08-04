package com.example.sumukh.flicker20;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImagesResponse {
    private int pages;
    private List<BaseImage> mImages = new ArrayList<BaseImage>();

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<BaseImage> getFlickrImages() {
        return mImages;
    }

    public ImagesResponse(){}

    public static ImagesResponse fromJson(JSONObject jsonObject) {

        ImagesResponse imagesResponse = new ImagesResponse();
        //Extraction
        try {
            JSONObject flickrResponse = jsonObject.getJSONObject("photos");
            imagesResponse.setPages(flickrResponse.getInt("pages"));
            JSONArray flickrImages = flickrResponse.getJSONArray("photo");
            for (int i = 0; i < flickrImages.length(); i++) {
                JSONObject photo = flickrImages.getJSONObject(i);
                imagesResponse.getFlickrImages().add(BaseImage.fromJson(photo));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imagesResponse;
    }

}
