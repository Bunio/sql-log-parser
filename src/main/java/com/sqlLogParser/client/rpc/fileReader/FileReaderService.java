package com.sqlLogParser.client.rpc.fileReader;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sqlLogParser.shared.logs.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub on 30.04.2017.
 */

@RemoteServiceRelativePath("FileReaderService")
public interface FileReaderService extends RemoteService
{
    public List<Log> getLogsFromFile(String path);
    public String parseLog(Log log);
}
