package com.example.nikhil.recyclerview;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by nikhil on 28/3/17.
 */

public class Utils{
    public static ArrayList<object> data(String x){
        URL url=create(x);
        String jsonresponse="";
        jsonresponse = makeHttpRequest(url);
        return extractdata(jsonresponse);
    }

    private static ArrayList<object> extractdata(String jsonresponse) {
        ArrayList<object> arr=new ArrayList<object>();
        try {
            JSONObject jobj=new JSONObject(jsonresponse);
            JSONArray jarr=jobj.getJSONArray("features");
            for(int i=0;i<jarr.length();i++){
                JSONObject object=jarr.getJSONObject(i);
                JSONObject object1 = object.getJSONObject("properties");
                JSONObject object2=object.getJSONObject("geometry");
                JSONArray cord=object2.getJSONArray("coordinates");
             //    long lon=945; long lat=878;
                  long lon = cord.getLong(0);
                  long lat = cord.getLong(1);


                String mag= object1.getString("mag");
                String place=object1.getString("place");
                long time = Long.parseLong(object1.getString("time"));
                String Url=object1.getString("url");
                arr.add(new object(mag,place,Url,time,lon,lat));
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return arr;
    }

    private static String makeHttpRequest(URL url) {
        String json="";
        HttpURLConnection httpurlconnection=null;
        InputStream inputstream=null;
        try {
            httpurlconnection=(HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("GET");
            httpurlconnection.connect();
            inputstream=httpurlconnection.getInputStream();
            json=readfromstream(inputstream);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static String readfromstream(InputStream inputstream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputstream!= null) {
            InputStreamReader streamreader = new InputStreamReader(inputstream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamreader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL create(String usgsRequestUrl) {
        URL u=null;
        try{
            u=new URL(usgsRequestUrl);
        } catch (MalformedURLException e) {
            return null;
        }
        return  u;
    }
}
