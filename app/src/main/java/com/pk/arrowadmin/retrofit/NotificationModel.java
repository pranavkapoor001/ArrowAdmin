package com.pk.arrowadmin.retrofit;


import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("to") //  "to" changed to token
    private String token;

    @SerializedName("notification")
    private NotificationModel notification;

    private String title;
    private String message;

    public NotificationModel(String token, String title, String message) {
        this.token = token;
        this.title = title;
        this.message = message;
    }
}
