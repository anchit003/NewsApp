package com.example.anchitchawla.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.nio.charset.Charset;

/**
 * Created by Anchit Chawla on 15-12-2017.
 */

public final class QueryUtil  {
    private static final String Log_Tag=QueryUtil.class.getSimpleName();

    private QueryUtil(){}
    public static List<News> fetchdata(String reqUrl)
    {
        URL url=makeUrl(reqUrl);
        String response=null;
        try
        {
            response=makeHttpreq(url);
        }
        catch (IOException e)
        {
            Log.e(Log_Tag,"Error in request.",e);
        }
        List<News> news=extract(response);
        return  news;
    }

    public static URL makeUrl(String s1)
    {
        URL url=null;
        try
        {
            url=new URL(s1);
        }
        catch (MalformedURLException e)
        {
            Log.e(Log_Tag,"Error in creating the URL", e);
        }
        return url;
    }
    private static String makeHttpreq(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(Log_Tag, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static List<News> extract (String s)
    {
        if (TextUtils.isEmpty(s))
        {
            return null;
        }
        ArrayList<News>news1=new ArrayList<News>();
        try
        {
            JSONObject jsonObject=new JSONObject(s);
            JSONObject jsonObject1=jsonObject.getJSONObject("response");
            JSONArray jsonObject2=jsonObject1.getJSONArray("results");
            for(int i=0;i<jsonObject2.length();i++)
            {
                JSONObject currn=jsonObject2.getJSONObject(i);
                String section=currn.getString("sectionName");
                String type=currn.getString("type");
                String tit=currn.getString("webTitle");
                String url=currn.getString("webUrl");
                News news=new News(section,tit,type,url);
                news1.add(news);
            }
        }
        catch (JSONException e)
        {
            Log.e("QueryUtil", "Problem parsing the earthquake JSON results", e);
        }
        return news1;
    }
}
