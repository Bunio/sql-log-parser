package com.sqlLogParser.client.rpc.fileReader;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created by Jakub on 30.04.2017.
 */

@RemoteServiceRelativePath("FileReaderService")
public interface FileReaderService extends RemoteService
{
    public String fileToString(String path);
}
