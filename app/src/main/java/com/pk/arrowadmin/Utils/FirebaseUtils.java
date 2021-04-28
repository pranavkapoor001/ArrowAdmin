package com.pk.arrowadmin.Utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pk.arrowadmin.retrofit.FirebaseApi;
import com.pk.arrowadmin.retrofit.RetrofitService;
import com.pk.arrowadmin.retrofit.RootModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirebaseUtils {

    // Vars
    private static final String TAG = "FirebaseUtils";
    private final FirebaseApi firebaseApi;

    public FirebaseUtils() {
        Retrofit retrofit = RetrofitService.getInstance();
        firebaseApi = retrofit.create(FirebaseApi.class);
    }

    /**
     * Subscribes to device topic with device codename to receive messages from FCM
     */
    public void subscribeToDevice() {
        FirebaseMessaging.getInstance().subscribeToTopic(Build.DEVICE)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "subscribeToDevice: failed!");
                        } else
                            Log.d(TAG, "subscribeToDevice: successful: " + Build.DEVICE);
                    }
                });
    }

    /**
     * Subscribes to Arrow announcement topic to receive messages from FCM
     */
    public void subscribeToArrow() {
        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseApi.ARROW_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "subscribeToArrow: failed!");
                        } else
                            Log.d(TAG, "subscribeToArrow: successful");
                    }
                });
    }

    /**
     * Posts message to FCM
     *
     * @param topic   FCM topic, can be device codename or Arrow announcement
     * @param title   Title for notification
     * @param message Message for notification
     */
    public void sendMessage(String topic, String title, String message) {
        firebaseApi.sendNotification(new RootModel(topic, title, message)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful())
                    Log.d(TAG, "onResponse: call successful");
                else
                    Log.e(TAG, "onResponse: call failed! CODE: " + response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: call failed! " + t.getMessage());
            }
        });
    }
}