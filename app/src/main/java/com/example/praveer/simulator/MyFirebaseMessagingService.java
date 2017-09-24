package com.example.praveer.simulator;

/**
 * Created by Praveer on 9/24/2017.
 */

import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.hypertrack.lib.HyperTrackFirebaseMessagingService;

public class MyFirebaseMessagingService extends HyperTrackFirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /**
         * Call super.onMessageReceived() method
         * SDK uses this method to handle HyperTrack notifications
         * Refer to the https://dashboard.hypertrack.com/onboarding/fcm-android for more info.
         */
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {
            String sdkNotification = remoteMessage.getData().get(HT_SDK_NOTIFICATION_KEY);
            if (sdkNotification != null && sdkNotification.equalsIgnoreCase("true")) {
                /**
                 * HyperTrack notifications are received here
                 * Dont handle these notifications. This might end up in a crash
                 */
                return;
            }
        }

        // Handle your notifications here.
        System.out.println("Notifi received");
        Toast.makeText(this,"FIREBAse NOTIFICAtioN received",Toast.LENGTH_SHORT).show();

    }
}
