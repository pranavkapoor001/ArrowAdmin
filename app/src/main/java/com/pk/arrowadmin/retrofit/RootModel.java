package com.pk.arrowadmin.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * This class MUST follow json format required by fcm notification
 */
public class RootModel {

    @SerializedName("to")
    private final String topic;

    @SerializedName("notification")
    private final NotificationModel notification = new NotificationModel();

    public RootModel(String topic, String title, String message) {
        this.topic = topic;
        notification.title = title;
        notification.message = message;
    }

    public static class NotificationModel {
        String title;
        @SerializedName("body")
        String message;
    }
}