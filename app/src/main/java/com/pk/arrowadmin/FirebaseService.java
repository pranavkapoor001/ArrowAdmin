package com.pk.arrowadmin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseService";

    /*
     * App state	Notification	    Data	            Both
     * Foreground	onMessageReceived	onMessageReceived	onMessageReceived
     * Background	System tray	        onMessageReceived	Notification: system tray
     *                                                      (Data: in extras of the intent)
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Message received: ");

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Title: " + remoteMessage.getNotification().getTitle());
            Log.e(TAG, "Message Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
