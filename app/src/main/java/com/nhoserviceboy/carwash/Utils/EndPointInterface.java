package com.nhoserviceboy.carwash.Utils;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EndPointInterface {


      /* @Headers({"Content-Type: application/json"
     })*/
    // https://api.cashfree.com/api/v2/cftoken/order


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/api/v2/cftoken/order")
    Call<CarwashRespose> getToken(@Header("x-client-id") String client_id,
                                  @Header("x-client-secret") String secret,
                                  @Body JsonObject option);








}



