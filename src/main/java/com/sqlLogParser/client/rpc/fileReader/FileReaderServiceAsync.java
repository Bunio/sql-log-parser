package com.sqlLogParser.client.rpc.fileReader;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sqlLogParser.shared.logs.Log;
import java.util.List;

/**
 * Created by Jakub on 30.04.2017.
 */
public interface FileReaderServiceAsync
{
    public void getLogsFromFile(String path, AsyncCallback<List<Log>> callback);
}
