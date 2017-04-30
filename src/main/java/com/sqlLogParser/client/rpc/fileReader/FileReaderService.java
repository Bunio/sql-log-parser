package com.sqlLogParser.client.rpc.fileReader;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sqlLogParser.logs.Log;
import java.util.List;

/**
 * Created by Jakub on 30.04.2017.
 */

@RemoteServiceRelativePath("FileReaderService")
public interface FileReaderService extends RemoteService
{
    public List<Log> getLogsFromFile(String path);
}
