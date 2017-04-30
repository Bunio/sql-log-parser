package com.sqlLogParser.server.rpc.logs;

import com.sqlLogParser.shared.logs.Log;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jakub on 30.04.2017.
 */

public class LogParser
{
    private static Logger logger = Logger.getLogger("LOG PARSER");

    public static String parseQuery(Log log)
    {

        String query = log.getContent();

        // Get param list
        List<String> params = getParamsFrom(log);

        // Remove everything before SELECT/UPDATE ETC
        query = trimToQuery(query);

        // Remove params
        query = cutParamsFrom(query);

        // Replace question marks with params
        query = replacePlaceholdersWithParams(query, params);

        return query.trim();
    }

    public static String trimToQuery(String log)
    {
        return log.replaceFirst(".*\\s+(?=DELETE |UPDATE |SELECT |INSERT )", "");
    }

    public static List<String> getParamsFrom(Log log)
    {
        String params = log.getContent()
                .replaceFirst("(.*)params=", "") // Remove Everything before params=
                .replaceAll("\\[", "")  // Remove all left square brackets
                .replaceAll("]", "")    // Remove all right square brackets
                .replaceAll("\\(.*?\\)", "") // Remove everything inside brackets
                .replaceAll("\\s+", "") // Remove all whitespace characters
                .trim();

        return Arrays.asList(params.split(","));
    }

    public static String cutParamsFrom(String log)
    {
        return  log.replaceFirst("\\[params=(.*)", "");
    }

    public static String replacePlaceholdersWithParams(String log, List<String> params)
    {
        for(String param : params)
        {
            log = log.replaceFirst("\\?", "'" + param + "'");
        }

        return log;
    }

}
