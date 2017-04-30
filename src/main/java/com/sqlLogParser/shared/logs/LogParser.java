package com.sqlLogParser.shared.logs;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jakub on 30.04.2017.
 */

public class LogParser
{
    private static Logger logger = Logger.getLogger("LOG PARSER");

    public static String parseLog(Log log)
    {

        logger.log(Level.INFO, "Parsing log of id: " + Long.toString(log.getId()));
        return log.getContent();
    }
}
