package com.example.sumukh.flicker20;
import org.json.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewFragment extends Fragment implements View.OnClickListener  {
    private View returnView;
    private GridView mGridView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton fab;

    private boolean hasMoreItems;
    private boolean mLoading;
    private boolean pressed=false;

    private PhotosAdapter mPhotosAdapter;
    private int page = 1;
    List<BaseImage>mImageList;
    private boolean restore;
    public ViewFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retain this
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        returnView = inflater.inflate(R.layout.fragment_grid, container, false);
        fab = (FloatingActionButton) returnView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setVisibility(View.GONE);
        return returnView;
    }
    @Override
    public void onClick(View v)
    {
        pressed=true;
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeContent();
    }

    @Override
    public void onPause() {
        restore = true;
        super.onPause();
    }

    private void initializeContent() {
        mGridView = (GridView) returnView.findViewById(R.id.grid);
        mSwipeRefreshLayout = (SwipeRefreshLayout) returnView.findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setEnabled(false);
        if (restore && mImageList.size() > 0) {
            update();
            restore = false;
        } else {
            loadPhotos(1);
        }
    }

    private void loadPhotos(int page) {
        mSwipeRefreshLayout.setRefreshing(true);
        mLoading = true;
        recentPhotosRequest(page);
    }

    private void recentPhotosRequest(int page) {
        RequestHandler.getInstance().doRequest().flickrRecentPhotos(page, listener, errorListener);
    }

    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(final JSONObject jsonObject) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mImageList == null || page == 1) {
                        mImageList = new ArrayList<BaseImage>();
                    }
                    ImagesResponse imagesResponse = ImagesResponse.
                            fromJson(jsonObject);
                    final List<BaseImage> response = imagesResponse.getFlickrImages();

                    hasMoreItems = imagesResponse.getPages() > page;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImageList.addAll(response);
                                update();

                        }
                    });

                    mLoading = false;
                }
            }).start();

        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            mLoading = false;
            Toast.makeText(getActivity(), "Error in the Listener",
                    Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void update() {
        if (mPhotosAdapter == null) {
            mPhotosAdapter = new PhotosAdapter(getActivity(), mImageList);
        }
        mSwipeRefreshLayout.setRefreshing(false);
        if (mGridView.getAdapter() == null) {
            mGridView.setAdapter(mPhotosAdapter);
        } else {
            mPhotosAdapter.notifyDataSetChanged();
        }

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (mLoading || visibleItemCount == 0 || totalItemCount == 0) {
                    return;
                }

                // At the end
                if (firstVisibleItem + visibleItemCount >= totalItemCount && hasMoreItems ) {
                    if(page<10) {
                        fab.setVisibility(View.VISIBLE);
                    }
                    if(pressed) {
                        pressed = false;
                        loadPhotos(++page);
                    }
                }
            }
        });
    }
}
