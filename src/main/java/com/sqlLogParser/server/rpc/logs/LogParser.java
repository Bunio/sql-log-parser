package com.sqlLogParser.server.rpc.logs;

import com.sqlLogParser.shared.logs.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        String resoult = log.getContent();

        resoult = cutLog(resoult);

        return resoult;
    }

    public static List<String> getParamsFrom(Log log)
    {
        String params = log.getContent()
                .replaceFirst("(.*)params=", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "")
                .replaceAll("\\(.*?\\)", "")
                .replaceAll("\\s+", "")
                .trim();

        return Arrays.asList(params.split(","));
    }

    public static String replacePlaceholdersWithParams(List<String> params)
    {
        return "lol";
    }

    private static String cutLog(String resoult)
    {
        return resoult.replaceFirst("(.*)SELECT","SELECT");
    }


}
