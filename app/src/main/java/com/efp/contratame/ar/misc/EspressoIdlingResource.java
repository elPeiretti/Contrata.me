package com.efp.contratame.ar.misc;

import androidx.test.espresso.idling.CountingIdlingResource;

//https://medium.com/swlh/adding-espresso-ui-tests-with-idling-resources-c87d9fc2cce7
public class EspressoIdlingResource {

    private final String RESOURCE = "GLOBAL";
    private CountingIdlingResource countingIdlingResource = new CountingIdlingResource(RESOURCE);
    private static EspressoIdlingResource instance = new EspressoIdlingResource();

    private EspressoIdlingResource(){}

    public static EspressoIdlingResource getInstance(){
        return instance;
    }

    public void increment(){
        countingIdlingResource.increment();
    }
    public void decrement(){
        if(!countingIdlingResource.isIdleNow())
            countingIdlingResource.decrement();
    }

    public CountingIdlingResource getCountingIdlingResource(){
        return countingIdlingResource;
    }
}
