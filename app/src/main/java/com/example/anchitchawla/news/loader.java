package com.example.anchitchawla.news;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Anchit Chawla on 16-12-2017.
 */

public class loader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_TAG = loader.class.getName();

    private String mUrl;

    public loader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<News> news = QueryUtil.fetchdata(mUrl);
        return news;
    }
}
