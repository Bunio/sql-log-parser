package com.sqlLogParser.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created by Jakub on 30.04.2017.
 */

@RemoteServiceRelativePath("MyService")
public interface MyService extends RemoteService
{
    public String myMethod(String s);
}
