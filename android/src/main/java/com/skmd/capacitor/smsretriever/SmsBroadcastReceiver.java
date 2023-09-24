package com.skmd.capacitor.smsretriever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "Capacitor/SmsRetriever";
    private static final String SMS_RETRIEVED_ACTION = SmsRetriever.SMS_RETRIEVED_ACTION;
    private static final String SMS_BODY_KEY = "body";
    private PluginCall CALL;

    public void setCALL(PluginCall CALL) {
        this.CALL = CALL;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "SMS Retriever onReceive");
       
        if (SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    // Get SMS message contents
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    JSObject result = new JSObject();
                    result.put(SMS_BODY_KEY, message);
                    CALL.resolve(result);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Waiting for SMS timed out (5 minutes)
                    Log.d(TAG, "SMS Retriever timeout");
                    CALL.reject("TIMEOUT");
                    break;
            }
        }
    }
}
