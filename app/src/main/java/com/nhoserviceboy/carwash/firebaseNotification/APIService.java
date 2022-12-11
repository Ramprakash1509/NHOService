package com.nhoserviceboy.carwash.firebaseNotification;

import static com.nhoserviceboy.carwash.firebaseNotification.Constant.CONTENT_TYPE;
import static com.nhoserviceboy.carwash.firebaseNotification.Constant.SERVER_KEY;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService
{
    @Headers(
            {
                    "Content-Type:"+CONTENT_TYPE,
                    "Authorization:key="+SERVER_KEY // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

