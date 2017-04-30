package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by Jakub on 30.04.2017.
 */
public interface MyServiceAsync
{
    public void myMethod(String s, AsyncCallback<String> callback);
}
