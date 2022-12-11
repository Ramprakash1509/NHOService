package com.nhoserviceboy.carwash.firebaseNotification;

public class Data {
    /*private String Title;
    private String Message;

    public Data(String title, String message)
    {
        Title = title;
        Message = message;
    }

    public Data() {
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
*/
    String title;
    String body;

    public Data(String title, String body)
    {

        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }
}
