package com.app.pojo;

import java.io.Serializable;

public class NewEntry implements Serializable
{
    private static final long serialVersionUID = 1L;

    public NewEntry()
    {
	
    }
    
    private String name;
    private String mobileNo;
    private String comments;
    private String imeiNo;
    private String lat = "0";
    private String lon = "0";
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getMobileNo()
    {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }
    public String getComments()
    {
        return comments;
    }
    public void setComments(String comments)
    {
        this.comments = comments;
    }
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
        return 	"===============: Entry details :===============\n" +
        	"\n Name : " + this.name +
                "\n Mobile : " + this.mobileNo +
                "\n Comment : " + this.comments +
                "\n IMEI : " + this.imeiNo +
                "\n Lat : " + this.lat +
                "\n Lon : " + this.lon ;
    }
}