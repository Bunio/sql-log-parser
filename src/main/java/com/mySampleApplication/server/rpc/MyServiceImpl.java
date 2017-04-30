package com.mySampleApplication.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.client.MyService;

/**
 * Created by Jakub on 30.04.2017.
 */
public class MyServiceImpl extends RemoteServiceServlet implements MyService
{
    @Override
    public String myMethod(String s) {
        return "Hello " + s;
    }
}
