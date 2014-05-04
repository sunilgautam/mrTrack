package com.app.pojo;

import java.io.Serializable;

public class MyPosition implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String imeiNo;
    private String lat = "0";
    private String lon = "0";
    
    public String getImeiNo()
    {
        return imeiNo;
    }
    public void setImeiNo(String imeiNo)
    {
        this.imeiNo = imeiNo;
    }
    public String getLat()
    {
        return lat;
    }
    public void setLat(String lat)
    {
        this.lat = lat;
    }
    public String getLon()
    {
        return lon;
    }
    public void setLon(String lon)
    {
        this.lon = lon;
    }
    
    @Override
    public String toString()
    {
        return 	"===============: MyPosition details :===============\n" +
                "\n IMEI : " + this.imeiNo +
                "\n Lat : " + this.lat +
                "\n Lon : " + this.lon ;
    }
}
