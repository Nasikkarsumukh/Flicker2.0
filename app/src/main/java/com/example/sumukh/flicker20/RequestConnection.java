package com.example.sumukh.flicker20;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;

import java.io.File;
public class RequestConnection {
    //Url Generator
    private static final String API_KEY = "7b85e389607020e3b5a12c5a40e260db";
    private static final String FORMAT = "json";
    private static final String EXTRAS = "url_m";
    private static final int PER_PAGE = 120;
    private static final String GET_RECENT = "flickr.photos.getRecent";
    private static final String NO_JSON_CALLBACK = "nojsoncallback=1";
    private static final String AMPERSAND = "&";
    private static final String BASE_URL = "https://api.flickr.com/services/rest/";

    public static final String RECENT_PHOTOS_URL = BASE_URL + "?" +
            "method=" + GET_RECENT + AMPERSAND +
            "api_key=" + API_KEY + AMPERSAND +
            "extras=" + EXTRAS + AMPERSAND +
            "per_page=" + PER_PAGE + AMPERSAND +
            "format=" + FORMAT + AMPERSAND +
            NO_JSON_CALLBACK + AMPERSAND +
            "page=";

    private RequestQueue mRequestQueue;
    private RequestQueue mImageLoaderQueue;

    // Disk Use
    private static final int DEFAULT_DISK_USAGE_BYTES = 5 * 1024 * 1024;

    // folder name
    private static final String CACHE_DIR = "PhotoCollection";

    //constructor
    RequestConnection(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mImageLoaderQueue = newPhotosRequestQueue(context.getApplicationContext());
        ImageManager.initializeWith(context.getApplicationContext(), mImageLoaderQueue);
    }



    private static RequestQueue newPhotosRequestQueue(Context context) {
        // define cache
        File rootCache = context.getExternalCacheDir();
        if (rootCache == null) {
            android.util.Log.w("Flicker Recent:", "Can't find External Cache Dir, "
                    + "switching to application specific cache directory");
            rootCache = context.getCacheDir();
        }

        File cacheDir;
        cacheDir = new File(rootCache, CACHE_DIR);
        cacheDir.mkdirs();

        HttpStack stack = new HurlStack();
        Network network = new BasicNetwork(stack);
        DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, DEFAULT_DISK_USAGE_BYTES);
        RequestQueue queue = new RequestQueue(diskBasedCache, network);
        queue.start();

        return queue;
    }

    public void flickrRecentPhotos(int page,
                                   Response.Listener listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RECENT_PHOTOS_URL  + page,
                null,
                listener,
                errorListener
        );
        jsonObjectRequest.setRetryPolicy(setTimeoutPolicy(15000));
        mRequestQueue.add(jsonObjectRequest);
    }

    private RetryPolicy setTimeoutPolicy(int timeInMilisec) {

        return new DefaultRetryPolicy(timeInMilisec,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
}
