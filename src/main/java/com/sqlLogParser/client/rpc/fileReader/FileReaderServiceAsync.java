package com.sqlLogParser.client.rpc.fileReader;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by Jakub on 30.04.2017.
 */
public interface FileReaderServiceAsync
{
    public void fileToString(String path, AsyncCallback<String> callback);
}
