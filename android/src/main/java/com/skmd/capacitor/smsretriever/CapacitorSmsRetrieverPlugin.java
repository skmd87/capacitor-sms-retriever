package com.skmd.capacitor.smsretriever;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.util.Log;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * This class is a Capacitor plugin for retrieving SMS messages on Android devices.
 * It registers a broadcast receiver to listen for incoming SMS messages and extracts the message body.
 * The retrieved SMS message is returned to the calling Capacitor app as a plugin result.
 * This plugin provides two methods: startListening and stopListening.
 * startListening method starts the SMS retriever client and registers the broadcast receiver.
 * stopListening method unregisters the broadcast receiver.
 */
@CapacitorPlugin(name = "CapacitorSmsRetriever")
public class CapacitorSmsRetrieverPlugin extends Plugin {

    private static final String TAG = "Capacitor/SmsRetriever";
    private static final String SMS_RETRIEVED_ACTION = SmsRetriever.SMS_RETRIEVED_ACTION;
    private static final String SMS_BODY_KEY = "smsBody";
    private BroadcastReceiver smsReceiver;

    /**
     * Starts listening for incoming SMS messages using the SmsRetriever API.
     * Registers a broadcast receiver to receive the SMS message and extract the verification code.
     * @param call The plugin call object.
     */
    @PluginMethod
    public void startListening(PluginCall call) {
        SmsRetrieverClient client = SmsRetriever.getClient(getContext());
        Task<Void> task = client.startSmsRetriever();

        task.addOnSuccessListener(
            new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "SMS Retriever starts successfully");
                    smsReceiver = new SmsBroadcastReceiver();
                    ((SmsBroadcastReceiver) smsReceiver).setCALL(call);
                    IntentFilter filter = new IntentFilter(SMS_RETRIEVED_ACTION);
                    getContext().registerReceiver(smsReceiver, filter);
                    Log.d(TAG, "SMS Registered successfully");
                }
            }
        );
        task.addOnFailureListener(
            new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "SMS Retriever failed to start", e);
                    call.reject("SMS Retriever failed to start", e);
                }
            }
        );
    }


    /**
     * Stops listening for incoming SMS messages.
     * 
     * @param call The plugin call object.
     */
    @PluginMethod
    public void stopListening(PluginCall call) {
        if (smsReceiver != null) {
            getContext().unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
        call.resolve();
    }
}
