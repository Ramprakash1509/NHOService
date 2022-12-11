package com.nhoserviceboy.carwash.Admin.Model;

public class Latlong
{    String id_loction;
     String latitude;
     String longitude;
     String Ulongitude;
     String ulatitude;
     int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUlatitude() {
        return ulatitude;
    }

    public void setUlatitude(String ulatitude) {
        this.ulatitude = ulatitude;
    }

    public String getUlongitude() {
        return Ulongitude;
    }

    public void setUlongitude(String ulongitude) {
        Ulongitude = ulongitude;
    }

    String service_boy_id;
    String service_id;

    public String getId_loction() {
        return id_loction;
    }

    public void setId_loction(String id_loction) {
        this.id_loction = id_loction;
    }

    String service_dateandtime;


    public String getService_boy_id()
    {
        return service_boy_id;
    }

    public void setService_boy_id(String service_boy_id) {
        this.service_boy_id = service_boy_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_dateandtime() {
        return service_dateandtime;
    }

    public void setService_dateandtime(String service_dateandtime) {
        this.service_dateandtime = service_dateandtime;
    }

    public Latlong(String id_loction, String latitude, String longitude, String ulongitude, String ulatitude, int status, String service_boy_id, String service_id, String service_dateandtime) {
        this.id_loction = id_loction;
        this.latitude = latitude;
        this.longitude = longitude;
        Ulongitude = ulongitude;
        this.ulatitude = ulatitude;
        this.status = status;
        this.service_boy_id = service_boy_id;
        this.service_id = service_id;
        this.service_dateandtime = service_dateandtime;
    }

    public Latlong()
    {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
