package com.example.nikhil.recyclerview;

/**
 * Created by nikhil on 11/3/17.
 */

public class object {

    private String range,location,detail,url;
    long time;
    long lon;

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    long lat;

    public object(String range, String location, String url, long time, long lon, long lat) {
        this.range = range;
        this.location = location;
        this.url = url;
        this.time = time;
        this.lon = lon;
        this.lat = lat;
    }

    public void setLocation(){

    }
    public void setDetail(){

    }
    public String getRange(){return range;}
    public String getLocation(){
        return location;
    }
    public String getDetail(){
        return detail;
    }
    public long getTime(){
        return time;
    }
    public String getUrl(){
        return url;
    }




}
