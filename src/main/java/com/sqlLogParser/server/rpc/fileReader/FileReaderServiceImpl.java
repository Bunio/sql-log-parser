package com.sqlLogParser.server.rpc.fileReader;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sqlLogParser.client.rpc.fileReader.FileReaderService;
import com.sqlLogParser.server.rpc.logs.LogParser;
import com.sqlLogParser.shared.logs.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Jakub on 30.04.2017.
 */
public class FileReaderServiceImpl extends RemoteServiceServlet implements FileReaderService
{
    @Override
    public ArrayList<Log> getLogsFromFile(String path)
    {
        ArrayList<Log> logs = new ArrayList<Log>();

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            Long id = 0L;

            String line = reader.readLine();


            while(line != null)
            {
                logs.add(new Log(id, line));

                id++;
                line = reader.readLine();
            }

        }

        catch (FileNotFoundException ex)
        {
            // Handle FileNotFoundException
        }

        catch (Exception ex)
        {
            // Handle other exceptions
        }

        return logs;
    }

    @Override
    public String parseLog(Log log) {
        return LogParser.parse(log);
    }
}
