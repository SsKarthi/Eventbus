package com.mindnotix.eventbus;

/**
 * Created by Karthi on 6/1/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class ChargingReceiver extends BroadcastReceiver {
    private boolean isConnected = false;

    private EventBus bus = EventBus.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        ChargingEvent event = null;

        // Get current time
        Time now = new Time();
        now.setToNow();
        String timeOfEvent = now.format("%H:%M:%S");

        String eventData = "@" + timeOfEvent + " this device ";
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            event = new ChargingEvent(eventData + "charging.");
        } else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            event = new ChargingEvent(eventData + "discharging.");
        } else{
            if (isNetworkAvailable(context)) {
                event = new ChargingEvent(eventData + "Network enabled.");
            }else if(!isNetworkAvailable(context)){
                event = new ChargingEvent(eventData + "Network disabled.");
            }
        }
        // Post the event
        bus.post(event);
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if (!isConnected) {
                            Log.v("LOG_TAG", "Now you are connected to Internet!");
                            Toast.makeText(context, "Internet availablle via Broadcast receiver", Toast.LENGTH_SHORT).show();
                            isConnected = true;
                            // do your processing here ---
                            // if you need to post any data to the server or get
                            // status
                            // update from the server
                        }
                        return true;
                    }
                }
            }
        }
        Log.v("LOG_TAG", "You are not connected to Internet!");
        Toast.makeText(context, "Internet NOT availablle via Broadcast receiver", Toast.LENGTH_SHORT).show();
        isConnected = false;
        return false;
    }


}