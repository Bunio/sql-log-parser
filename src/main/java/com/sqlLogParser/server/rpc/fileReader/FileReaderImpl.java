package com.sqlLogParser.server.rpc.fileReader;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sqlLogParser.client.rpc.fileReader.FileReaderService;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Jakub on 30.04.2017.
 */
public class FileReaderImpl extends RemoteServiceServlet implements FileReaderService
{

    private static Logger logger = Logger.getLogger("FILE READER");

    @Override
    public String fileToString(String path)
    {
        logger.log(Level.INFO, "aaab");
        return "FILE CONTENT";
    }
}
