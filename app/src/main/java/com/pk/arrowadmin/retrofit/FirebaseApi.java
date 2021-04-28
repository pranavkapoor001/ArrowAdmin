package com.pk.arrowadmin.retrofit;

import com.pk.arrowadmin.BuildConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FirebaseApi {
    String BASE_URL = "https://fcm.googleapis.com/";
    String ARROW_TOPIC = "/topics/ArrowOS";

    @Headers({"Authorization: key=" + BuildConfig.SERVER_KEY, "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body RootModel root);
}
