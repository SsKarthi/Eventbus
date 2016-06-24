package com.mindnotix.eventbus;

/**
 * Created by Karthi on 6/1/2016.
 */
public class ChargingEvent {
    private String data;

    public ChargingEvent(String data){
        this.data = data;
    }

    public String getData(){
        return data;
    }
}