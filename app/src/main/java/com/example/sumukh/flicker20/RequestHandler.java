package com.example.sumukh.flicker20;

import android.content.Context;

public class RequestHandler {
    private static RequestHandler instance;
    private RequestConnection mRequestConnection;

    private RequestHandler(Context context) {
        mRequestConnection = new RequestConnection(context);
    }

    public RequestConnection doRequest() {
        return mRequestConnection;
    }

    //singleton initialization
    public static synchronized RequestHandler getInstance(Context context) {
        if (instance == null) {
            instance = new RequestHandler(context);
        }
        return instance;
    }

    public static synchronized RequestHandler getInstance() {
        if (instance == null) {
            throw new IllegalStateException(RequestHandler.class.getSimpleName() +
                    " Not initialized");
        }
        return instance;
    }
}
